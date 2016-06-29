package sky.ox.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.beans.MockStatus;
import sky.ox.beans.Status;
import sky.ox.beans.StatusHelper;
import sky.ox.beans.User;
import sky.ox.helper.AVOSHelper;
import sky.ox.helper.AccountHelper;
import sky.ox.helper.Callback;
import sky.ox.ui.activity.FolloweeListActivity;
import sky.ox.ui.activity.SendStatusActivity;
import sky.ox.ui.activity.UserDetailActivity;
import sky.ox.ui.adapter.UserWorksAdapter;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/20/16.
 */
public class WorksFragment extends BaseFragment {

    @InjectView(R.id.publish_image)
    Button publishImage;
    @InjectView(R.id.publish_video)
    Button publishVideo;
    @InjectView(R.id.publish_audio)
    Button publishAudio;
    @InjectView(R.id.publish_paper)
    Button publishPaper;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    UserWorksAdapter adapter;
    User user;

    public static Fragment newInstance(int position) {
        return new WorksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = AccountHelper.getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_works, container, false);
        ButterKnife.inject(this, view);
        initViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getStatusList();
    }

    private void initViews() {
        publishImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    ToastUtil.show(R.string.please_login);
                }
//                Intent intent = new Intent(v.getContext(), FolloweeListActivity.class);
//                startActivity(intent);
                Intent intent = StatusHelper.createSendImageIntent(v.getContext());
                v.getContext().startActivity(intent);
            }
        });

        publishVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    ToastUtil.show(R.string.please_login);
                }
//                Intent intent = new Intent(v.getContext(), UserDetailActivity.class);
//                startActivity(intent);
                Intent intent = StatusHelper.createSendVideoIntent(v.getContext());
                v.getContext().startActivity(intent);
            }
        });

        publishAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    ToastUtil.show(R.string.please_login);
                }
//                AVOSHelper.sendstatus();
                Intent intent = StatusHelper.createSendAudioIntent(v.getContext());
                v.getContext().startActivity(intent);
            }
        });

        publishPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    ToastUtil.show(R.string.please_login);
                }
//                AVOSHelper.followTest1Users();
                Intent intent = StatusHelper.createSendTextIntent(v.getContext());
                v.getContext().startActivity(intent);
            }
        });

        adapter = new UserWorksAdapter();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration();

//        adapter.setData(MockStatus.createMockStatusList());
    }

    private void getStatusList() {
        user = AccountHelper.getCurrentUser();
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
        } else {
            adapter.setData(null);
        }
    }

    @Override
    protected void onLogin() {
        user = AccountHelper.getCurrentUser();
        getStatusList();
    }

    @Override
    protected void onLogout() {
        adapter.setData(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
