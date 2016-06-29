package sky.ox.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.beans.User;
import sky.ox.helper.AccountHelper;
import sky.ox.helper.Callback;
import sky.ox.ui.activity.AboutActivity;
import sky.ox.ui.activity.FolloweeListActivity;
import sky.ox.ui.activity.LoginActivity;
import sky.ox.ui.view.SelfItemView;
import sky.ox.utils.BuildConfigUtils;
import sky.ox.utils.ImageLoader;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/20/16.
 */
public class SelfFragment extends BaseFragment {

    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.login)
    Button login;
    @InjectView(R.id.followee)
    SelfItemView followee;
    @InjectView(R.id.logout)
    SelfItemView logout;
    @InjectView(R.id.about)
    SelfItemView about;
    @InjectView(R.id.version)
    TextView version;

    public static Fragment newInstance(int position) {
        return new SelfFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        initViews();
    }

    private void initViews() {
        if (this.version == null) {
            return;
        }

        this.logout.setText(R.string.logout_title);
        if (AccountHelper.isLogin()) {
            User user = AccountHelper.getCurrentUser();
            if (user != null) {
                this.image.setVisibility(View.VISIBLE);
                this.name.setVisibility(View.VISIBLE);
                this.login.setVisibility(View.GONE);
                this.logout.setVisibility(View.VISIBLE);

                ImageLoader.load(user.getAvatarUrl(), this.image);
                this.name.setText(user.getUsername());
                this.logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout(v.getContext());
                    }
                });
            }
        } else {
            this.image.setVisibility(View.GONE);
            this.name.setVisibility(View.GONE);
            this.login.setVisibility(View.VISIBLE);
            this.login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            this.logout.setVisibility(View.GONE);
        }

        followee.setText(R.string.followee_list_title);
        followee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountHelper.isLogin()) {
                    Intent intent = new Intent(v.getContext(), FolloweeListActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.show(R.string.please_login);
                }
            }
        });

        about.setText(R.string.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        version.setText(BuildConfigUtils.getVersionName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void logout(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.logout_title)
                .setMessage(R.string.sure_logout)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AccountHelper.logout(new Callback() {
                            @Override
                            public void onSuccess(Object object) {
                                initViews();
                            }

                            @Override
                            public void onFailed(int code, String message) {

                            }
                        });
                    }
                })
                .show();
    }
}
