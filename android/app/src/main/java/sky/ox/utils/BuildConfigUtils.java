package sky.ox.utils;

import sky.ox.BuildConfig;

/**
 * Created by sky on 4/15/16.
 */
public class BuildConfigUtils {

    public static boolean isDebug() {
        return BuildConfig.APP_DEBUG;
    }

    public static boolean isRelease() {
        return BuildConfig.APP_RELEASE;
    }

    public static String getChannel() {
        return BuildConfig.FLAVOR;
    }

    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public static boolean isMiui() {
        Class<?> cls = null;
        try {
            cls = Class.forName("miui.os.Build");
        } catch (ClassNotFoundException e) {
            // ignore
        }
        return cls != null;
    }
}
