package sky.ox.utils.web;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 6/8/16.
 */
public class WebViewOverrideUrlRules {

    /**
     * init override url rules.
     * @return List<OverrideUrlRule>
     */
    public static List<OverrideUrlRule> initOverrideUrlRules() {
        return new ArrayList<OverrideUrlRule>() {{
            add(new BackPress());
        }};
    }

    static class BackPress extends OverrideUrlRule {

        @Override
        public boolean matchUrl(String url) {
            return url.equals("http://localhost/api/backpress");
        }

        @Override
        public void doWork(WebView view, String url) {
            Context context = view.getContext();
            if (context instanceof Activity) {
                ((Activity) context).onBackPressed();
            }
        }
    }

}
