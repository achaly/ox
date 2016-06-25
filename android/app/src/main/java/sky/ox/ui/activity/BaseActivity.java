package sky.ox.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.jaeger.library.StatusBarUtil;

import rx.Subscription;
import rx.functions.Action1;
import sky.ox.R;
import sky.ox.helper.AccountHelper;

/**
 * Created by sky on 6/20/16.
 */
public class BaseActivity extends AppCompatActivity {
    private Subscription login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        login = AccountHelper.getLoginSubject().subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    onLogin();
                } else {
                    onLogout();
                }
            }
        });
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setTranslucent(this, 128);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        StatusBarUtil.setTranslucent(this, 128);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        StatusBarUtil.setTranslucent(this, 128);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (login != null && !login.isUnsubscribed()) {
            login.unsubscribe();
        }
        super.onDestroy();
    }

    protected void onLogin() {
    }

    protected void onLogout() {
    }

    void setupToolbar(Toolbar toolbar, boolean enableBackIcon) {
        setSupportActionBar(toolbar);

        if (enableBackIcon) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    ProgressDialog showLoadingDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading_text));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(true);
        dialog.show();

        return dialog;
    }
}
