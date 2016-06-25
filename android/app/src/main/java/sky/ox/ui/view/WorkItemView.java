package sky.ox.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.config.WorkItemConfig;
import sky.ox.utils.ImageLoader;

/**
 * Created by sky on 6/21/16.
 */
public class WorkItemView extends FrameLayout {
    @InjectView(R.id.thumbnail)
    ImageView thumbnail;
    @InjectView(R.id.paper)
    TextView paper;
    @InjectView(R.id.video_cover)
    ImageView videoCover;
    @InjectView(R.id.paper_title)
    TextView paperTitle;
    @InjectView(R.id.container)
    FrameLayout container;

    public WorkItemView(Context context) {
        super(context);
        init();
    }

    public WorkItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WorkItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WorkItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_work_item, this);
        ButterKnife.inject(view);

        this.paper.setEllipsize(TextUtils.TruncateAt.END);
    }

    public void setImage(String imageUrl) {
        this.thumbnail.setVisibility(VISIBLE);
        this.paperTitle.setVisibility(INVISIBLE);
        this.paper.setVisibility(INVISIBLE);
        this.videoCover.setVisibility(INVISIBLE);
        this.paperTitle.setText(null);
        this.paper.setText(null);

        setContainerHeight(WorkItemConfig.IMAGE_HEIGHT);

        ImageLoader.load(imageUrl, this.thumbnail);
    }

    public void setPaper(String title, String text) {
        this.thumbnail.setVisibility(INVISIBLE);
        this.paperTitle.setVisibility(VISIBLE);
        this.paper.setVisibility(VISIBLE);
        this.videoCover.setVisibility(INVISIBLE);
        this.thumbnail.setImageBitmap(null);

        this.paperTitle.setText(title);
        this.paper.setText(text);

        setContainerHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setVideo(String thumbnail) {
        this.thumbnail.setVisibility(VISIBLE);
        this.paperTitle.setVisibility(INVISIBLE);
        this.paper.setVisibility(INVISIBLE);
        this.videoCover.setVisibility(VISIBLE);
        this.thumbnail.setImageBitmap(null);
        this.paperTitle.setText(null);
        this.paper.setText(null);

        if (thumbnail != null) {
            ImageLoader.load(thumbnail, this.thumbnail);
        } else {
            this.thumbnail.setImageBitmap(null);
        }

        this.videoCover.setImageResource(R.mipmap.video);

        setContainerHeight(WorkItemConfig.VIDEO_HEIGHT);
    }

    public void setAudio() {
        this.thumbnail.setVisibility(VISIBLE);
        this.paperTitle.setVisibility(INVISIBLE);
        this.paper.setVisibility(INVISIBLE);
        this.videoCover.setVisibility(VISIBLE);
        this.thumbnail.setImageBitmap(null);
        this.paperTitle.setText(null);
        this.paper.setText(null);

        this.videoCover.setImageResource(R.mipmap.music);

        setContainerHeight(WorkItemConfig.AUDIO_HEIGHT);
    }

    private void setContainerHeight(int height) {
        ViewGroup.LayoutParams lp = container.getLayoutParams();
        lp.height = height;
        container.setLayoutParams(lp);
    }
}
