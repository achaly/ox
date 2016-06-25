package sky.ox.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import sky.ox.ui.fragment.MainFragment;
import sky.ox.ui.fragment.SelfFragment;
import sky.ox.ui.fragment.WorksFragment;

/**
 * Created by sky on 6/20/16.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
    ViewPager.OnPageChangeListener listener;

    public FragmentAdapter(FragmentManager fm, ViewPager.OnPageChangeListener listener) {
        super(fm);
        this.listener = listener;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MainFragment.newInstance(position);
            case 1:
                return WorksFragment.newInstance(position);
            case 2:
                return SelfFragment.newInstance(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (listener != null) {
            listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (listener != null) {
            listener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (listener != null) {
            listener.onPageScrollStateChanged(state);
        }
    }
}
