package sky.ox.tutorial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.braunster.tutorialview.object.Tutorial;
import com.braunster.tutorialview.object.TutorialBuilder;
import com.braunster.tutorialview.object.TutorialIntentBuilder;

import sky.ox.R;
import sky.ox.utils.SPUtil;

/**
 * Created by sky on 6/29/16.
 */
public class TutorialViewHelper {


    public static void showTutorialAbove(View view, String text) {
        Context context = view.getContext();

        TutorialBuilder tBuilder = new TutorialBuilder();

        tBuilder.setTitle("")
                .setViewToSurround(view)
                .setInfoText(text)
                .setBackgroundColor(R.color.black_80_transparent)
                .setTutorialTextColor(Color.WHITE)
//                .setTutorialTextTypeFaceName("fonts/roboto_light.ttf")
                .setTutorialTextSize(20)
                .setAnimationDuration(200)
                .setTutorialInfoTextPosition(Tutorial.InfoPosition.ABOVE)
                .setTutorialGotItPosition(Tutorial.GotItPosition.BOTTOM);


        TutorialIntentBuilder intentBuilder = new TutorialIntentBuilder(context)
                .changeSystemUiColor(true)
                .setTutorial(tBuilder.build());

        Intent intent = intentBuilder.getIntent();
        context.startActivity(intent);

        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.dummy, R.anim.dummy);
        }
    }

    private static final String WALK_THROUGH_KEY = "has_walk_through";

    public static boolean hasWalkThrough() {
        return SPUtil.get().getBoolean(WALK_THROUGH_KEY);
    }

    public static void walkThroughTutorial(View main, String mainText, View works, String worksText, View self, String selfText) {
        SPUtil.get().putBoolean(WALK_THROUGH_KEY, true);

        Context context = main.getContext();

        Tutorial t1 = new TutorialBuilder().setTitle("")
                .setViewToSurround(main)
                .setInfoText(mainText)
                .setBackgroundColor(R.color.black_80_transparent)
                .setTutorialTextColor(Color.WHITE)
                .setTutorialTextSize(20)
                .setAnimationDuration(200)
                .setTutorialInfoTextPosition(Tutorial.InfoPosition.ABOVE)
                .setTutorialGotItPosition(Tutorial.GotItPosition.BOTTOM)
                .build();

        Tutorial t2 = new TutorialBuilder().setTitle("")
                .setViewToSurround(works)
                .setInfoText(worksText)
                .setBackgroundColor(R.color.black_80_transparent)
                .setTutorialTextColor(Color.WHITE)
                .setTutorialTextSize(20)
                .setAnimationDuration(200)
                .setTutorialInfoTextPosition(Tutorial.InfoPosition.ABOVE)
                .setTutorialGotItPosition(Tutorial.GotItPosition.BOTTOM)
                .build();

        Tutorial t3 = new TutorialBuilder().setTitle("")
                .setViewToSurround(self)
                .setInfoText(selfText)
                .setBackgroundColor(R.color.black_80_transparent)
                .setTutorialTextColor(Color.WHITE)
                .setTutorialTextSize(20)
                .setAnimationDuration(200)
                .setTutorialInfoTextPosition(Tutorial.InfoPosition.ABOVE)
                .setTutorialGotItPosition(Tutorial.GotItPosition.BOTTOM)
                .build();


        TutorialIntentBuilder intentBuilder = new TutorialIntentBuilder(context)
                .changeSystemUiColor(true)
                .setWalkThroughList(t1, t2, t3);

        Intent intent = intentBuilder.getIntent();
        context.startActivity(intent);

        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.dummy, R.anim.dummy);
        }
    }
}
