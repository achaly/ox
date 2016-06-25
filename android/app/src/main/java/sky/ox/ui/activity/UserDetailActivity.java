package sky.ox.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.beans.Status;
import sky.ox.beans.User;
import sky.ox.helper.AccountHelper;
import sky.ox.helper.Callback;
import sky.ox.ui.adapter.UserWorksAdapter;
import sky.ox.ui.view.UserItemView;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/21/16.
 */
public class UserDetailActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.user)
    UserItemView userItemView;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    UserWorksAdapter adapter;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = getIntent().getParcelableExtra("user");

        initViews();
        getStatusList();
        checkFollow();
    }

    private void initViews() {
        setContentView(R.layout.activity_user_detail);
        ButterKnife.inject(this);

        setupToolbar(toolbar, true);
        if (user != null) {
            toolbar.setTitle(user.getUsername());

            userItemView.setImage(user.getAvatarUrl());
            userItemView.setName(user.getUsername());

            adapter = new UserWorksAdapter();
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.addItemDecoration();


//            adapter.setData(MockStatus.createMockStatusList());
        }

    }

    private void getStatusList() {
        if (user != null) {
            user.getStatusList(new Callback() {
                @Override
                public void onSuccess(Object object) {
                    List<Status> statuses = (List<Status>) object;
                    adapter.setData(statuses);
                }

                @Override
                public void onFailed(int code, String message) {
                    ToastUtil.show(R.string.get_data_failed);
                }
            });
        }
    }

    private void checkFollow() {
        User me = AccountHelper.getCurrentUser();
        if (user != null && me != null) {
            me.hasFollowUser(user, new Callback() {
                @Override
                public void onSuccess(Object object) {
                    setUnFollowBtn();
                }

                @Override
                public void onFailed(int code, String message) {
                    setFollowBtn();
                }
            });
        }
    }

    private void setFollowBtn() {
        userItemView.setFollowBtnText(R.string.add_follow_text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followUser();
            }
        });
    }

    private void setUnFollowBtn() {
        userItemView.setFollowBtnText(R.string.cancel_follow_text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unFollowUser();
            }
        });
    }

    private void followUser() {
        User me = AccountHelper.getCurrentUser();
        if (me != null && user != null) {
            me.followUser(user, new Callback() {
                @Override
                public void onSuccess(Object object) {
                    setUnFollowBtn();
                }

                @Override
                public void onFailed(int code, String message) {
                    ToastUtil.show(R.string.operation_failed);
                }
            });
        }
    }

    private void unFollowUser() {
        User me = AccountHelper.getCurrentUser();
        if (me != null && user != null) {
            me.unFollowUser(user, new Callback() {
                @Override
                public void onSuccess(Object object) {
                    setFollowBtn();
                }

                @Override
                public void onFailed(int code, String message) {
                    ToastUtil.show(R.string.operation_failed);
                }
            });
        }
    }
}
