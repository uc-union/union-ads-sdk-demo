package com.ucweb.union.ads.sample;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class UnionAdsSdkSampleActivity extends FragmentActivity implements ActionBar.TabListener {

  private ViewPager mViewPager;
  private UnionFragmentPagerAdapter mViewPagerAdapter;
  private ActionBar mActionBar;

  private String[] mTagNameArray = {"Banner", "Interstitial", "Native", "Video","NativeTemplate"};

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mViewPagerAdapter = new UnionFragmentPagerAdapter(getSupportFragmentManager());

    mViewPager = new ViewPager(this);
    mViewPager.setId(R.id.main_view_pager);
    mViewPager.setAdapter(mViewPagerAdapter);
    mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        mActionBar.setSelectedNavigationItem(position);
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });

    FrameLayout contentContainer = new FrameLayout(this);
    contentContainer.addView(mViewPager);

    setContentView(contentContainer,
                   new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                              ViewGroup.LayoutParams.MATCH_PARENT));

    mActionBar = getActionBar();
    mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    for (String tabName : mTagNameArray) {
      mActionBar.addTab(mActionBar.newTab().setText(tabName).setTabListener(this));
    }
  }

  @Override
  public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
    mViewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
  }

  @Override
  public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
  }
}
