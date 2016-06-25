package sky.ox.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.beans.Status;
import sky.ox.beans.StatusType;
import sky.ox.beans.User;
import sky.ox.helper.AccountHelper;
import sky.ox.utils.ImageLoader;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/24/16.
 */
public class ShowImageTextActivity extends BaseActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.image)
    ImageView image;
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

        if (status == null) {
            finish();
        }

        initViews();
    }

    private void initViews() {
        setContentView(R.layout.activity_show_image_text);
        ButterKnife.inject(this);

        if (StatusType.IMAGE.equals(status.statusType)) {
            ImageLoader.load(status.images.get(0), image);
        } else {
            image.setImageBitmap(null);
        }

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
}
