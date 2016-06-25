package sky.ox.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import sky.ox.App;

/**
 * Created by sky on 4/15/16.
 */
public class ProcessUtils {

    public static String getProcessName() {
        int pid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) App.getAppContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo i : activityManager.getRunningAppProcesses()) {
            if (i.pid == pid) {
                return i.processName;
            }
        }

        return null;
    }

    public static boolean isMainProcess() {
        return TextUtils.equals(getProcessName(), App.getAppContext().getPackageName());
    }
}
