package sky.ox;

import android.app.Application;
import android.content.Context;

import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;
import com.facebook.stetho.Stetho;

import sky.ox.helper.AVOSHelper;
import sky.ox.utils.BuildConfigUtils;
import sky.ox.utils.ProcessUtils;

/**
 * Created by sky on 6/15/16.
 */
public class App extends Application {

    static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        LogUtils.getLogConfig()
                .configAllowLog(BuildConfigUtils.isDebug())
                .configTagPrefix("OX")
                .configShowBorders(true)
                .configLevel(LogLevel.TYPE_VERBOSE);

        if (BuildConfigUtils.isDebug()) {
            Stetho.initializeWithDefaults(this);
        }

        if (ProcessUtils.isMainProcess()) {
            initMainProcess();
        }


    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        if (ProcessUtils.isMainProcess()) {
            terminateMainProcess();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    public static Context getAppContext() {
        return app.getApplicationContext();
    }

    public static Application getApplication() {
        return app;
    }

    private void initMainProcess() {
        AVOSHelper.init(this);
//        AVOSHelper.signUpUsers();
    }

    private void terminateMainProcess() {
    }
}
