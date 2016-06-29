package sky.ox.tutorial;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.logutils.LogUtils;
import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.SimplePagerFragment;

/**
 * Created by sky on 6/28/16.
 */
public class CustomPresentationPagerFragment extends SimplePagerFragment {
    public interface OnPageFinishedListener {
        void onFinished();
    }

    private final int PAGE_COUNT = 3;
    OnPageFinishedListener listener;

    public void setOnPageFinishedListener(OnPageFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getViewPager().setOffscreenPageLimit(PAGE_COUNT);
        getSkipButton().setVisibility(View.GONE);
        return view;
    }

    @Override
    protected int getPagesCount() {
        return PAGE_COUNT;
    }

    @Override
    protected PageFragment getPage(int position) {
        switch (position) {
            case 0: {
                return new FirstPagerFragment();
            }
            case 1: {
                return new SecondPagerFragment();
            }
            case 2: {
                return new ThirdPagerFragment();
            }
        }
        return null;
    }

    @Override
    protected int getPageColor(int position) {
        return Color.rgb(248, 248, 248);
    }

    @Override
    protected boolean isInfiniteScrollEnabled() {
        return false;
    }

    @Override
    public void onDetach() {
        if (listener != null) {
            listener.onFinished();
        }
        super.onDetach();
    }
}
