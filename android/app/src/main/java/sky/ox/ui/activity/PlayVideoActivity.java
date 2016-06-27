package sky.ox.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.beans.Status;
import sky.ox.beans.User;
import sky.ox.helper.AccountHelper;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/27/16.
 */
public class PlayVideoActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.video_view)
    VideoView videoView;
    @InjectView(R.id.message)
    TextView message;
    @InjectView(R.id.show_user)
    Button showUser;
    @InjectView(R.id.share)
    Button share;

    User user;
    Status status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = AccountHelper.getCurrentUser();
        status = getIntent().getParcelableExtra("status");

        initViews();

    }

    private void initViews() {
        setContentView(R.layout.activity_play_video);
        ButterKnife.inject(this);

        toolbar.setTitle(status.title);
        message.setText(status.message);
        setupToolbar(toolbar, true);

        showUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.source == null) {
                    ToastUtil.show(R.string.please_login);
                    return;
                }
                Intent intent = new Intent(v.getContext(), UserDetailActivity.class);
                intent.putExtra("user", status.source);
                v.getContext().startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(R.string.not_implement);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        playVideo(status.video);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        stopVideo();

        super.onDestroy();
    }

    private void playVideo(String url) {
        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(this));
        videoView.start();
    }

    private void stopVideo() {
        videoView.stopPlayback();
    }
}
