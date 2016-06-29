package sky.ox.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.beans.InboxType;
import sky.ox.beans.Status;
import sky.ox.beans.StatusHelper;
import sky.ox.beans.StatusType;
import sky.ox.beans.User;
import sky.ox.helper.AccountHelper;
import sky.ox.helper.Callback;
import sky.ox.helper.StorageHelper;
import sky.ox.utils.ImageLoader;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/22/16.
 */
public class SendStatusActivity extends BaseActivity {
    private static final int REQUEST_CHOOSER = 1234;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.select)
    Button select;
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.uri_str)
    TextView uriStr;
    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;
    @InjectView(R.id.upload_btn)
    Button uploadBtn;
    @InjectView(R.id.title)
    EditText titleText;
    @InjectView(R.id.content)
    EditText contentText;
    @InjectView(R.id.send_status_btn)
    Button sendStatusBtn;

    User user;
    String statusType;
    Uri uriSelected;

    boolean uploadSuccessful = false;
    String uploadUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = AccountHelper.getCurrentUser();
        statusType = getIntent().getStringExtra("statusType");
        if (statusType == null) {
            statusType = "";
        }

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHOOSER: {
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    LogUtils.d("uri: %s", uri.toString());
                    selectUri(uri);
                }
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        setContentView(R.layout.activity_send_status);
        ButterKnife.inject(this);

        setupToolbar(toolbar, true);

        uploadBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        uploadBtn.setOnClickListener(new UploadClick());
        sendStatusBtn.setOnClickListener(new SendClick());

        switch (statusType) {
            case StatusType.IMAGE: {
                toolbar.setTitle(R.string.select_image_title);
                select.setText(R.string.select_image_title);
                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent getIntent = StorageHelper.createGetImageIntent();
                        Intent intent = Intent.createChooser(getIntent, getString(R.string.select_image_title));
                        startActivityForResult(intent, REQUEST_CHOOSER);
                    }
                });
                break;
            }
            case StatusType.VIDEO: {
                toolbar.setTitle(R.string.select_video_title);
                select.setText(R.string.select_video_title);
                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent getIntent = StorageHelper.createGetVideoIntent();
                        Intent intent = Intent.createChooser(getIntent, getString(R.string.select_video_title));
                        startActivityForResult(intent, REQUEST_CHOOSER);
                    }
                });
                break;
            }
            case StatusType.AUDIO: {
                toolbar.setTitle(R.string.select_audio_title);
                select.setText(R.string.select_audio_title);
                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent getIntent = StorageHelper.createGetAudioIntent();
                        Intent intent = Intent.createChooser(getIntent, getString(R.string.select_audio_title));
                        startActivityForResult(intent, REQUEST_CHOOSER);
                    }
                });
                break;
            }
            case StatusType.TEXT:
            default: {
                toolbar.setTitle(R.string.send_status_btn);
                select.setVisibility(View.GONE);
                uploadBtn.setVisibility(View.GONE);
            }
        }
    }

    private void selectUri(Uri uri) {
        uriSelected = uri;

        switch (statusType) {
            case StatusType.IMAGE: {
                ImageLoader.load(uri.toString(), image);
                image.setVisibility(View.VISIBLE);
                uploadBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                break;
            }
            case StatusType.VIDEO: {
                Bitmap bitmap = FileUtils.getThumbnail(getApplicationContext(), uri);
                if (bitmap != null) {
                    image.setImageBitmap(bitmap);
                } else {
                    uriStr.setText(uri.toString());
                }
                uploadBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                break;
            }
            case StatusType.AUDIO: {
                uriStr.setText(uri.toString());
                uploadBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    class UploadClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (uriSelected == null) {
                return;
            }
            if (uploadSuccessful) {
                return;
            }

            File file = FileUtils.getFile(getApplicationContext(), uriSelected);
            progressBar.setVisibility(View.VISIBLE);
            StorageHelper.uploadFile(file, new StorageHelper.UploadingCallback() {
                @Override
                public void onProgress(String key, double percent) {
                    progressBar.setProgress((int) (percent * 100));
                }

                @Override
                public void onFinished(boolean isSuccessful, String url) {
                    if (isSuccessful) {
                        uploadSuccessful = true;
                        uploadUrl = url;
                        progressBar.setProgress(100);
                        ToastUtil.show(R.string.upload_file_successful);

                        LogUtils.d("uploadFile onSuccess");
                    } else {
                        uploadSuccessful = false;
                        uploadUrl = null;
                        ToastUtil.show(R.string.upload_file_failed);

                        LogUtils.d("uploadFile onFailed");
                    }
                }
            });
        }
    }

    class SendClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(statusType)) {
                return;
            }
            if (!StatusType.TEXT.equals(statusType) && !uploadSuccessful) {
                return;
            }

            String title = titleText.getText().toString();
            String message = contentText.getText().toString();
            if (TextUtils.isEmpty(title)) {
                ToastUtil.show(R.string.please_input_title);
            }
            if (TextUtils.isEmpty(message)) {
                ToastUtil.show(R.string.please_input_message);
            }
            Status status = null;

            switch (statusType) {
                case StatusType.IMAGE: {
                    status = StatusHelper.createImagesStatus(title, message, uploadUrl);
                    break;
                }
                case StatusType.VIDEO: {
                    status = StatusHelper.createVideosStatus(title, message, uploadUrl);
                    break;
                }
                case StatusType.AUDIO: {
                    status = StatusHelper.createAudiosStatus(title, message, uploadUrl);
                    break;
                }
                case StatusType.TEXT: {
                    status = StatusHelper.createTextStatus(title, message);
                    break;
                }
                default:
            }

            if (status != null) {
                Dialog dialog = showLoadingDialog();
                user.sendStatus(status, new Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        dialog.dismiss();
                        ToastUtil.show(R.string.send_status_successful);
                        LogUtils.d("sendStatus onSuccess");

                        finish();
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        dialog.dismiss();
                        ToastUtil.show(R.string.send_status_failed);
                        LogUtils.d("sendStatus onFailed");
                    }
                });
            }

        }
    }
}
