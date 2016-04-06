package com.ucweb.union.ads.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ucweb.union.ads.AdError;
import com.ucweb.union.ads.AdListener;
import com.ucweb.union.ads.AdRequest;
import com.ucweb.union.ads.BannerAdView;
import com.ucweb.union.ads.UnionAd;

public class BannerFragment extends Fragment {
  private BannerAdView mBannerAdView;
  private FrameLayout mContentContainer;
  private Button mBtnLoad;
  private TextView mTvStatus;

  /**
   * You should use your own **PUB** in production
   */
  private static final String PUB = "ssr@debugbanner";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mBannerAdView = new BannerAdView(getActivity());
    mBannerAdView.setAdListener(mAdListener);

    initView();
    initAction();
  }

  private void initView() {
    mBtnLoad = new Button(getActivity());
    mBtnLoad.setText(getString(R.string.load));

    mTvStatus = new TextView(getActivity());
    mTvStatus.setText(getString(R.string.ad_start_loading));
    mTvStatus.setGravity(Gravity.CENTER);
    mTvStatus.setVisibility(View.INVISIBLE);
  }

  private void initAction() {
    mBtnLoad.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mBtnLoad.setVisibility(View.INVISIBLE);

        AdRequest request = AdRequest.newBuilder().pub(PUB).build();
        mBannerAdView.loadAd(request);

        mTvStatus.setVisibility(View.VISIBLE);
      }
    });
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
    mContentContainer = new FrameLayout(getActivity());
    mContentContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                 ViewGroup.LayoutParams.MATCH_PARENT));

    mContentContainer.addView(mTvStatus,
                              new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                           FrameLayout.LayoutParams.WRAP_CONTENT,
                                                           Gravity.TOP));
    mContentContainer.addView(mBtnLoad,
                              new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                           FrameLayout.LayoutParams.WRAP_CONTENT,
                                                           Gravity.CENTER));
    mContentContainer.addView(mBannerAdView,
                              new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                           FrameLayout.LayoutParams.WRAP_CONTENT,
                                                           Gravity.BOTTOM));

    return mContentContainer;
  }

  @Override
  public void onDestroyView() {
    mContentContainer.removeAllViews();
    mContentContainer = null;
    super.onDestroyView();
  }

  @Override
  public void onDestroy() {
    mBannerAdView = null;
    super.onDestroy();
  }

  private final AdListener mAdListener = new AdListener() {
    @Override
    public void onAdLoaded(UnionAd unionAd) {
      if (unionAd == mBannerAdView) {
        mTvStatus.setText(getString(R.string.ad_load_success));
      }
    }

    @Override
    public void onAdClosed(UnionAd unionAd) {

    }

    @Override
    public void onAdShowed(UnionAd unionAd) {
      if (unionAd == mBannerAdView) {
        mTvStatus.setText(getString(R.string.ad_showed));
      }
    }

    @Override
    public void onAdClicked(UnionAd unionAd) {
      if (unionAd == mBannerAdView) {
        Toast.makeText(getActivity(), getString(R.string.ad_clicked), Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    public void onAdError(UnionAd unionAd, AdError adError) {
      if (unionAd == mBannerAdView) {
        mTvStatus.setText(getString(R.string.ad_load_error, adError.getErrorMessage()));
      }
    }
  };
}
