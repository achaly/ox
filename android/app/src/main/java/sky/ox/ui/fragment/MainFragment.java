package sky.ox.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.logutils.LogUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.beans.Status;
import sky.ox.beans.StatusHelper;
import sky.ox.beans.User;
import sky.ox.helper.AccountHelper;
import sky.ox.helper.Callback;
import sky.ox.ui.adapter.UserWorksAdapter;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/20/16.
 */
public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    UserWorksAdapter adapter;
    User user;

    public static Fragment newInstance(int position) {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = AccountHelper.getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        initViews();

        getAllStatus();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initViews() {
        swipeContainer.setOnRefreshListener(this);

        adapter = new UserWorksAdapter();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration();


//        adapter.setData(MockStatus.createMockStatusList());
    }

    private void getAllStatus() {
        StatusHelper.getAllStatus(new Callback() {
            @Override
            public void onSuccess(Object object) {
                List<Status> statuses = (List<Status>) object;
                adapter.setData(statuses);

                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailed(int code, String message) {
                ToastUtil.show(R.string.get_data_failed);

                swipeContainer.setRefreshing(false);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onRefresh() {
        LogUtils.d("onRefresh");
        getAllStatus();

    }
}
