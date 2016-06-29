package sky.ox.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ysyao.bottomtabbar.OnSelectableTextViewClickedListener;
import com.ysyao.bottomtabbar.SelectableBottomTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sky.ox.R;
import sky.ox.tutorial.CustomPresentationPagerFragment;
import sky.ox.tutorial.TutorialViewHelper;
import sky.ox.ui.adapter.FragmentAdapter;
import sky.ox.utils.SPUtil;

public class MainActivity extends BaseActivity implements CustomPresentationPagerFragment.OnPageFinishedListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.main_tab)
    SelectableBottomTextView mainTab;
    @InjectView(R.id.works_tab)
    SelectableBottomTextView worksTab;
    @InjectView(R.id.self_tab)
    SelectableBottomTextView selfTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            initTutorial();
        }
        initViews();
    }

    private void initTutorial() {
        CustomPresentationPagerFragment fragment = new CustomPresentationPagerFragment();
        fragment.setOnPageFinishedListener(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        toolbar.setTitle(R.string.main_fragment_name);
        setupToolbar(toolbar, false);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mainTab.setTextViewSelected(true);
                        worksTab.setTextViewSelected(false);
                        selfTab.setTextViewSelected(false);
                        toolbar.setTitle(R.string.main_fragment_name);
                        break;
                    case 1:
                        mainTab.setTextViewSelected(false);
                        worksTab.setTextViewSelected(true);
                        selfTab.setTextViewSelected(false);
                        toolbar.setTitle(R.string.works_fragment_name);
                        break;
                    case 2:
                        mainTab.setTextViewSelected(false);
                        worksTab.setTextViewSelected(false);
                        selfTab.setTextViewSelected(true);
                        toolbar.setTitle(R.string.self_fragment_name);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(adapter);

        mainTab.setOnTextViewClickedListener(new OnSelectableTextViewClickedListener() {
            @Override
            public void onTextViewClicked(View v) {
                viewPager.setCurrentItem(0, false);
            }
        });

        worksTab.setOnTextViewClickedListener(new OnSelectableTextViewClickedListener() {
            @Override
            public void onTextViewClicked(View v) {
                viewPager.setCurrentItem(1, false);
            }
        });

        selfTab.setOnTextViewClickedListener(new OnSelectableTextViewClickedListener() {
            @Override
            public void onTextViewClicked(View v) {
                viewPager.setCurrentItem(2, false);
            }
        });
    }

    @Override
    public void onFinished() {
        if (hasWindowFocus() && !TutorialViewHelper.hasWalkThrough()) {
            TutorialViewHelper.walkThroughTutorial(mainTab, getString(R.string.tutorial_main_tab),
                    worksTab, getString(R.string.tutorial_work_tab),
                    selfTab, getString(R.string.tutorial_self_tab));
        }
    }
}
