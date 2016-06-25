package sky.ox.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.utils.ImageLoader;

/**
 * Created by sky on 6/20/16.
 */
public class UserItemView extends FrameLayout {
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.follow_btn)
    TextView followBtn;
    @InjectView(R.id.name_container)
    LinearLayout nameContainer;
    @InjectView(R.id.category1)
    TextView category1;
    @InjectView(R.id.category2)
    TextView category2;
    @InjectView(R.id.category3)
    TextView category3;
    @InjectView(R.id.category4)
    TextView category4;

    public UserItemView(Context context) {
        super(context);
        init();
    }

    public UserItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UserItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UserItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_user_item, this);
        ButterKnife.inject(view);

    }

    public void setImage(String imageUrl) {
        ImageLoader.load(imageUrl, this.image);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

//    public void setOnFollowListener(OnClickListener listener) {
//        if (listener != null) {
//            this.followBtn.setText(getResources().getString(R.string.cancel_follow_text));
//            this.followBtn.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onClick(v);
//                }
//            });
//        } else {
//            this.followBtn.setText(null);
//            this.followBtn.setOnClickListener(null);
//        }
//    }

    public void setFollowBtnText(String text, OnClickListener listener) {
        this.followBtn.setText(text);
        this.followBtn.setOnClickListener(listener);
    }

    public void setFollowBtnText(@StringRes int res, OnClickListener listener) {
        this.followBtn.setText(res);
        this.followBtn.setOnClickListener(listener);
    }

    public void setCategory(String... category) {
        switch (category.length) {
            case 0:
                category1.setVisibility(INVISIBLE);
                category2.setVisibility(INVISIBLE);
                category3.setVisibility(INVISIBLE);
                category4.setVisibility(INVISIBLE);
                break;
            case 1:
                category1.setVisibility(VISIBLE);
                category2.setVisibility(INVISIBLE);
                category3.setVisibility(INVISIBLE);
                category4.setVisibility(INVISIBLE);
                category1.setText(category[0]);
                break;
            case 2:
                category1.setVisibility(VISIBLE);
                category2.setVisibility(VISIBLE);
                category3.setVisibility(INVISIBLE);
                category4.setVisibility(INVISIBLE);
                category1.setText(category[0]);
                category2.setText(category[1]);
                break;
            case 3:
                category1.setVisibility(VISIBLE);
                category2.setVisibility(VISIBLE);
                category3.setVisibility(VISIBLE);
                category4.setVisibility(INVISIBLE);
                category1.setText(category[0]);
                category2.setText(category[1]);
                category3.setText(category[2]);
                break;
            case 4:
                category1.setVisibility(VISIBLE);
                category2.setVisibility(VISIBLE);
                category3.setVisibility(VISIBLE);
                category4.setVisibility(VISIBLE);
                category1.setText(category[0]);
                category2.setText(category[1]);
                category3.setText(category[2]);
                category4.setText(category[3]);
                break;
            default:
                // not implement.
        }
    }
}
