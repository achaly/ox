package sky.ox.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;

/**
 * Created by sky on 6/20/16.
 */
public class SelfItemView extends FrameLayout {
    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.container)
    LinearLayout container;

    public SelfItemView(Context context) {
        super(context);
        init();
    }

    public SelfItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelfItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelfItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_self_item, this);
        ButterKnife.inject(view);

    }

    public void setText(CharSequence sequence) {
        text.setText(sequence);
    }

    public void setText(int strRes) {
        text.setText(strRes);
    }

    public void setOnClickListener(OnClickListener listener) {
        container.setOnClickListener(listener);
    }

}
