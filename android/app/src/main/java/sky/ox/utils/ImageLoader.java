package sky.ox.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import sky.ox.App;

/**
 * Created by sky on 5/9/16.
 */
public class ImageLoader {

    public static void load(Context context, String url, ImageView view) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Picasso.with(context)
                .load(url)
                .into(view);
    }

    public static void load(Context context, int imgRes, ImageView view) {
        if (imgRes == 0) {
            return;
        }
        Picasso.with(context)
                .load(imgRes)
                .into(view);
    }

    public static void load(String url, ImageView view) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        load(App.getAppContext(), url, view);
    }

    public static void load(@DrawableRes int imgRes, ImageView view) {
        load(App.getAppContext(), imgRes, view);
    }
}
