package sky.ox.utils;

import android.content.Context;
import android.widget.Toast;

import sky.ox.App;

/**
 * Created by sky on 4/14/16.
 */
public class ToastUtil {
    private static Toast lastToast = null;

    public static void clear() {
        if (lastToast != null) {
            lastToast.cancel();
        }
    }

    public static void show(Context context, int resId) {
        show(context, context.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getString(resId), duration);
    }

    public static void show(Context context, CharSequence text, int duration) {
        if (lastToast != null) {
            lastToast.cancel();
        }
        Toast aToast = Toast.makeText(context, text, duration);
        lastToast = aToast;
        aToast.show();
    }

    public static void show(CharSequence text) {
        show(App.getAppContext(), text);
    }

    public static void show(int resId) {
        show(App.getAppContext(), resId);
    }

    public static void show(CharSequence text, int duration) {
        show(App.getAppContext(), text, duration);
    }

    public static void show(int resId, int duration) {
        show(App.getAppContext(), resId, duration);
    }
}
