package sky.ox.utils.web;

import android.webkit.WebView;

/**
 * Created by sky on 6/8/16.
 */
public abstract class OverrideUrlRule {

    public boolean overrideUrl(WebView view, String url) {
        if (matchUrl(url)) {
            doWork(view, url);
            return true;
        }
        return false;
    }

    abstract boolean matchUrl(String url);

    abstract void doWork(WebView view, String url);

}
