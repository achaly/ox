package sky.ox.tutorial;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;

import sky.ox.R;

/**
 * Created by sky on 6/28/16.
 */
public class FirstPagerFragment extends PageFragment {
    @Override
    protected TransformItem[] provideTransformItems() {
        return new TransformItem[] {
                new TransformItem(R.id.cover1, false, -10),
                new TransformItem(R.id.cover2, false, -10),
                new TransformItem(R.id.cover3, false, -10),
                new TransformItem(R.id.cover4, false, -10),
                new TransformItem(R.id.cover5, false, -10)
        };
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tutorial_first;
    }
}
