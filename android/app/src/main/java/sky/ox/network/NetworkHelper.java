package sky.ox.network;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import sky.ox.App;

/**
 * Created by sky on 6/16/16.
 */
public class NetworkHelper {
    static final String TAG = "NetworkHelper";
    static OkHttpClient mOkHttpClient;

    static OkHttpClient createOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(20, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setWriteTimeout(30, TimeUnit.SECONDS);
        client.setCookieHandler(new ForwardingCookieHandler(App.getAppContext()));
//        client.networkInterceptors().add(new UserAgentInterceptor());

//        if (BuildConfigUtils.isDebug()) {
//            client.networkInterceptors().add(new StethoInterceptor());
//        }
        return client;
    }

    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (TAG) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = createOkHttpClient();
                }
            }
        }
        return mOkHttpClient;
    }

}
