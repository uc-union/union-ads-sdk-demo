package com.ucweb.union.ads.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class UnionFragmentPagerAdapter extends FragmentPagerAdapter {
  public UnionFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    Fragment f = null;
    switch (position) {
      case 0:
        f = new BannerFragment();
        break;
      case 1:
        f = new InterstitialFragment();
        break;
      case 2:
        f = new NativeFragment();
        break;
      case 3:
        f = new VideoFragment();
        break;
      default:
        break;
    }
    return f;
  }

  @Override
  public int getCount() {
    return 4;
  }
}
