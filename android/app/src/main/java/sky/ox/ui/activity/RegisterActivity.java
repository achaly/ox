package sky.ox.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.helper.AccountHelper;
import sky.ox.helper.Callback;
import sky.ox.helper.StorageHelper;
import sky.ox.utils.ImageLoader;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/21/16.
 */
public class RegisterActivity extends BaseActivity {
    private static final int REQUEST_CHOOSER = 1234;
    public static final int REGISTER_SUCCESSFUL = 0;
    public static final int REGISTER_FAILED = 1;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.input_user_name)
    EditText inputUserName;
    @InjectView(R.id.input_user_pwd)
    EditText inputUserPwd;
    @InjectView(R.id.input_user_pwd_again)
    EditText inputUserPwdAgain;
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.upload_btn)
    Button uploadBtn;
    @InjectView(R.id.register)
    Button register;

    Uri uriSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
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
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        toolbar.setTitle(R.string.register_name);
        setupToolbar(toolbar, true);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = StorageHelper.createGetImageIntent();
                Intent intent = Intent.createChooser(getIntent, getString(R.string.select_image_title));
                startActivityForResult(intent, REQUEST_CHOOSER);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void selectUri(Uri uri) {
        uriSelected = uri;

        if (uri != null) {
            ImageLoader.load(uri.toString(), image);
            image.setVisibility(View.VISIBLE);
        }
    }

    private void register() {
        String username = inputUserName.getText().toString();
        String pwd = inputUserPwd.getText().toString();
        String pwdAgain = inputUserPwdAgain.getText().toString();

        if (TextUtils.isEmpty(username)) {
            ToastUtil.show(R.string.please_input_username);
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.show(R.string.please_input_pwd);
            return;
        }

        if (!TextUtils.equals(pwd, pwdAgain)) {
            ToastUtil.show(R.string.pwd_not_equal_pwd_again);
            return;
        }

        if (uriSelected == null) {
            ToastUtil.show(R.string.please_select_avatar);
            return;
        }

        Dialog dialog = showLoadingDialog();
        File file = FileUtils.getFile(getApplicationContext(), uriSelected);
        StorageHelper.uploadFile(file, new StorageHelper.UploadingCallback() {
            @Override
            public void onProgress(String key, double percent) {
            }

            @Override
            public void onFinished(boolean isSuccessful, String url) {
                dialog.dismiss();

                if (isSuccessful) {
                    AccountHelper.signUp(username, pwd, null, url, new Callback() {
                        @Override
                        public void onSuccess(Object object) {
                            ToastUtil.show(R.string.register_successful);
                            setResult(REGISTER_SUCCESSFUL);
                            finish();
                        }

                        @Override
                        public void onFailed(int code, String message) {
                            ToastUtil.show(R.string.register_failed);
                        }
                    });
                } else {
                    ToastUtil.show(R.string.upload_file_failed);
                }
            }
        });
    }


}
