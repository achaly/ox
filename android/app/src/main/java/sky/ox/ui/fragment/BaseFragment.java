package sky.ox.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rx.Subscription;
import rx.functions.Action1;
import sky.ox.R;
import sky.ox.helper.AccountHelper;

/**
 * Created by sky on 6/20/16.
 */
public class BaseFragment extends Fragment {
    private Subscription login;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (login != null && !login.isUnsubscribed()) {
            login.unsubscribe();
        }
        super.onDestroy();
    }

    protected void onLogin() {
    }

    protected void onLogout() {
    }

    protected ProgressDialog showLoadingDialog() {
        if (getActivity() != null) {
            ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setMessage(getContext().getString(R.string.loading_text));
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(true);
            dialog.show();

            return dialog;
        }

        return null;
    }

}
