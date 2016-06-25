package sky.ox.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.beans.MockUser;
import sky.ox.beans.User;
import sky.ox.helper.AccountHelper;
import sky.ox.helper.Callback;
import sky.ox.ui.adapter.FolloweeListAdapter;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/20/16.
 */
public class FolloweeListActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    FolloweeListAdapter adapter;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = AccountHelper.getCurrentUser();

        initViews();

        getFolloweeList();
    }

    private void initViews() {
        setContentView(R.layout.activity_followee_list);
        ButterKnife.inject(this);

        toolbar.setTitle(R.string.followee_list_title);
        setupToolbar(toolbar, true);

        adapter = new FolloweeListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration();

//        adapter.setData(MockUser.createMockUserList());


    }

    private void getFolloweeList() {
        if (user != null) {
            user.getFolloweeList(new Callback() {
                @Override
                public void onSuccess(Object object) {
                    List<User> users = (List<User>) object;
                    adapter.setData(users);
                }

                @Override
                public void onFailed(int code, String message) {
                    ToastUtil.show(R.string.get_data_failed);
                }
            });
        }
    }

}
