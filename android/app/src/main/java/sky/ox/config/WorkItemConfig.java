package sky.ox.config;

import android.content.res.Resources;

/**
 * Created by sky on 6/23/16.
 */
public class WorkItemConfig {

    public static int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    public static int IMAGE_WIDTH = (int) (SCREEN_WIDTH / 2);
    public static int IMAGE_HEIGHT = (int) (IMAGE_WIDTH * 1.4);

    public static int VIDEO_WIDTH = (int) (SCREEN_WIDTH / 2);
    public static int VIDEO_HEIGHT = (int) (VIDEO_WIDTH / 1.5);

    public static int AUDIO_WIDTH = (int) (SCREEN_WIDTH / 2);
    public static int AUDIO_HEIGHT = (int) (AUDIO_WIDTH / 2);

    public static int TEXT_WIDTH = (int) (SCREEN_WIDTH / 2);
    public static int TEXT_HEIGHT = (int) (TEXT_WIDTH / 3);

    private WorkItemConfig() {
    }

}
