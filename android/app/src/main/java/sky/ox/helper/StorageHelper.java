package sky.ox.helper;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.apkfuns.logutils.LogUtils;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.util.Locale;

import rx.Single;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sky.ox.beans.UploadToken;

/**
 * Created by sky on 6/15/16.
 */
public class StorageHelper {
    static final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface UploadingCallback {
        void onProgress(String key, double percent);

        void onFinished(boolean isSuccessful, String url);
    }

    public static void uploadFile(String filePath, UploadingCallback progress) {
        uploadFile(new File(filePath), progress);
    }

    public static void uploadFile(File file, UploadingCallback progress) {
        Single.just(null)
                .map(new Func1<Object, UploadToken>() {
                    @Override
                    public UploadToken call(Object o) {
                        return QiniuHelper.getUploadToken();
                    }
                })
                .map(new Func1<UploadToken, Object>() {
                    @Override
                    public Object call(UploadToken uploadToken) {
                        if (uploadToken == null) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progress.onFinished(false, null);
                                }
                            });
                            return null;
                        }

                        UploadManager uploadManager = QiniuHelper.getUploadManager();

                        String mimeType = FileUtils.getMimeType(file);
                        UploadOptions options = new UploadOptions(null, mimeType, false, new UpProgressHandler() {
                            @Override
                            public void progress(String key, double percent) {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progress.onProgress(key, percent);
                                    }
                                });
                            }
                        }, null);

                        uploadManager.put(file, null, uploadToken.token, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    String fileKey = response.optString("key");
                                    String url = String.format(Locale.US, "http://%s/%s", uploadToken.domain, fileKey);
                                    LogUtils.d("uploadFile url: %s", url);
                                    mainHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            progress.onFinished(true, url);
                                        }
                                    });
                                } else {
                                    LogUtils.d("uploadFile error: %s", info.toString());
                                    mainHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            progress.onFinished(false, null);
                                        }
                                    });
                                }
                            }
                        }, options);
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe();

    }

    public static Intent createGetImageIntent() {
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    public static Intent createGetVideoIntent() {
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    public static Intent createGetAudioIntent() {
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    public static String transformImageUrl(String url, int longEdge, int shortEdge) {
        return String.format(Locale.US, "%s?imageView2/%d/w/%d/h/%d", url, 0, longEdge, shortEdge);
    }

}
