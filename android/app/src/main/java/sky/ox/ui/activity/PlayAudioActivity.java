package sky.ox.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sky.ox.R;
import sky.ox.beans.Status;
import sky.ox.beans.StatusType;
import sky.ox.beans.User;
import sky.ox.helper.AccountHelper;
import sky.ox.helper.Callback;
import sky.ox.utils.ImageLoader;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/27/16.
 */
public class PlayAudioActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;
    @InjectView(R.id.play_btn)
    ImageView playBtn;
    @InjectView(R.id.show_user)
    Button showUser;
    @InjectView(R.id.share)
    Button share;
    @InjectView(R.id.message)
    TextView message;

    User user;
    Status status;

    MediaPlayer mediaPlayer;
    Subscription updateProgressSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = AccountHelper.getCurrentUser();
        status = getIntent().getParcelableExtra("status");

        if (status == null) {
            finish();
        }

        initViews();
        startPlayAudio(new Callback() {
            @Override
            public void onSuccess(Object object) {
                updateProgress();
            }

            @Override
            public void onFailed(int code, String message) {

            }
        });
    }

    private void initViews() {
        setContentView(R.layout.activity_play_audio);
        ButterKnife.inject(this);

        progressBar.setProgress(0);
        progressBar.setMax(100);
        toolbar.setTitle(status.title);
        message.setText(status.message);
        setupToolbar(toolbar, true);

        setBtnPaused(false);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause(new Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        boolean paused = (boolean) object;
                        setBtnPaused(paused);
                    }

                    @Override
                    public void onFailed(int code, String message) {

                    }
                });
            }
        });

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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (updateProgressSubscription != null && !updateProgressSubscription.isUnsubscribed()) {
            updateProgressSubscription.unsubscribe();
        }

        stopPlayAudio();

        super.onDestroy();
    }

    private void startPlayAudio(Callback callback) {
        Uri uri = Uri.parse(status.audio);
        if (uri != null) {
            Observable.just(null)
                    .map(new Func1<Object, Object>() {
                        @Override
                        public Object call(Object o) {
                            try {
                                mediaPlayer = MediaPlayer.create(PlayAudioActivity.this, uri);
                                mediaPlayer.start();
                            } catch (Exception e) {
                            }
                            return null;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            callback.onSuccess(null);
                        }
                    });

        } else {
            callback.onFailed(-1, null);
        }

    }

    private void pause(Callback callback) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                callback.onSuccess(true);
            } else {
                mediaPlayer.start();
                callback.onSuccess(false);
            }
        }
        callback.onFailed(-1, null);
    }

    private void stopPlayAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void setBtnPaused(boolean paused) {
        if (paused) {
            playBtn.setImageResource(android.R.drawable.ic_media_play);
        } else {
            playBtn.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    private void updateProgress() {
        updateProgressSubscription = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        int d = mediaPlayer.getDuration();
                        int p = mediaPlayer.getCurrentPosition();
                        int percet = p * 100 / d;
                        percet = percet >= 0 ? percet : 0;
                        percet = percet <= 100 ? percet : 100;
                        progressBar.setProgress(percet);
                    }
                });
    }
}
