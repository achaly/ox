package sky.ox.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import sky.ox.App;

/**
 * Created by sky on 5/30/16.
 */
public class SPUtil {

    static class Instance {
        static SPUtil sp = new SPUtil();
    }

    String name;

    SPUtil setName(String name) {
        this.name = name;
        return this;
    }

    SharedPreferences getSP() {
        if (name == null) {
            return PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        } else {
            return App.getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        }
    }

    public static SPUtil get() {
        return Instance.sp.setName(null);
    }

    public static SPUtil get(String name) {
        return Instance.sp.setName(name);
    }

    public void clear() {
        getSP().edit().clear().apply();
    }

    public boolean hasKey(String key) {
        return getSP().contains(key);
    }

    public void removeKey(String key) {
        getSP().edit().remove(key).apply();
    }

    public void putBoolean(String key, boolean value) {
        getSP().edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return getSP().getBoolean(key, false);
    }

    public void putString(String key, String value) {
        getSP().edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return getSP().getString(key, null);
    }

}
