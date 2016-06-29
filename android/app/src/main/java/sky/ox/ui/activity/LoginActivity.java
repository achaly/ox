package sky.ox.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.helper.AVOSHelper;
import sky.ox.helper.AccountHelper;
import sky.ox.helper.Callback;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/20/16.
 */
public class LoginActivity extends BaseActivity {
    public static final int REQUEST_CODE = 101;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.input_user_name)
    EditText inputUserName;
    @InjectView(R.id.input_user_pwd)
    EditText inputUserPwd;
    @InjectView(R.id.register)
    TextView register;
    @InjectView(R.id.forget_pwd)
    TextView forgetPwd;
    @InjectView(R.id.login)
    Button login;
    @InjectView(R.id.login_by_weixin)
    TextView loginByWeixin;
    @InjectView(R.id.login_by_qq)
    TextView loginByQq;
    @InjectView(R.id.login_by_weibo)
    TextView loginByWeibo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RegisterActivity.REGISTER_SUCCESSFUL) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        setContentView(R.layout.activity_account_login);
        ButterKnife.inject(this);

        toolbar.setTitle(R.string.login_text);
        setupToolbar(toolbar, true);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(R.string.not_implement);
            }
        });

        loginByWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(R.string.not_implement);
            }
        });

        loginByQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(R.string.not_implement);
            }
        });

        loginByWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(R.string.not_implement);
            }
        });

        inputUserName.setText("hello@163.com");
        inputUserPwd.setText("123456");
    }

    private void login() {
        String user = inputUserName.getText().toString();
        String pwd = inputUserPwd.getText().toString();

        LogUtils.d("username: %s, pwd: %s", user, pwd);
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)) {
            return;
        }

        Dialog dialog = showLoadingDialog();
        AccountHelper.login(user, pwd, new Callback() {
            @Override
            public void onSuccess(Object object) {
                dialog.dismiss();
                ToastUtil.show(R.string.login_success_text);
                finish();
            }

            @Override
            public void onFailed(int code, String message) {
                dialog.dismiss();
                ToastUtil.show(R.string.login_failed_text);
            }
        });
    }

    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
