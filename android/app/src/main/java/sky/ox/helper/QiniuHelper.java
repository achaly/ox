package sky.ox.helper;

import com.apkfuns.logutils.LogUtils;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.util.Locale;

import sky.ox.beans.UploadToken;
import sky.ox.network.NetworkHelper;

/**
 * Created by sky on 6/16/16.
 */
public class QiniuHelper {

    static UploadManager manager;
    public static UploadManager create() {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认 256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认 512K
                .connectTimeout(10) // 链接超时。默认 10秒
                .responseTimeout(60) // 服务器响应超时。默认 60秒
//                .recorder(recorder)  // recorder 分片上传时，已上传片记录器。默认 null
//                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
        UploadManager uploadManager = new UploadManager(config);
        return uploadManager;
    }

    public static UploadManager getUploadManager() {
        if (manager == null) {
            synchronized (QiniuHelper.class) {
                if (manager == null) {
                    manager = create();
                }
            }
        }
        return manager;
    }

    static final String TOKEN_URL = "http://skyzp.leanapp.cn/app/upload/token";
    public static UploadToken getUploadToken() {
        try {
            OkHttpClient client = NetworkHelper.getOkHttpClient();
            Request request = new Request.Builder()
                    .url(TOKEN_URL)
                    .method("GET", null)
                    .build();

            Response response = client.newCall(request).execute();

            JSONObject object = new JSONObject(response.body().string()).optJSONObject("result");
            String token = object.optString("token");
            String domain = object.optString("domain");

            UploadToken uploadToken = new UploadToken(token, domain);
            LogUtils.d("UploadToken: %s", uploadToken.toString());

            return uploadToken;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String transformImageUrl(String url, int longEdge, int shortEdge) {
        if (url != null && !url.contains("?imageView2")) {
            return String.format(Locale.US, "%s?imageView2/%d/w/%d/h/%d", url, 0, longEdge, shortEdge);
        }
        return url;
    }

}
