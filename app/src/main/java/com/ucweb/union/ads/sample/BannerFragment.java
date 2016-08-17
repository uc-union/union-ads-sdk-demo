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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ucweb.union.ads.Ad;
import com.ucweb.union.ads.AdError;
import com.ucweb.union.ads.AdListener;
import com.ucweb.union.ads.AdRequest;
import com.ucweb.union.ads.BannerAdView;

public class BannerFragment extends Fragment {
  private BannerAdView mBannerAdView;
  private RelativeLayout mContentContainer;
  private FrameLayout mBannerContainer;
  private Button mBtnLoad;
  private TextView mTvStatus;

  /**
   * You should use your own **PUB** in production
   */
  private static final String PUB = "ssr@debugbanner";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  private void initView() {
    mContentContainer = (RelativeLayout) LayoutInflater.from(getActivity())
                                                       .inflate(R.layout.banner_fragment_content_layout,
                                                                null,
                                                                false);
    mBtnLoad = (Button) mContentContainer.findViewById(R.id.button);
    mBtnLoad.setText(getString(R.string.load));

    mTvStatus = (TextView) mContentContainer.findViewById(R.id.status_text);

    // Setup Banner Ad View
    mBannerContainer = (FrameLayout) mContentContainer.findViewById(R.id.fl_banner_container);
    mBannerAdView = new BannerAdView(getActivity());
    mBannerAdView.setAdListener(mAdListener);
    mBannerContainer.addView(mBannerAdView,
                             new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                          FrameLayout.LayoutParams.WRAP_CONTENT,
                                                          Gravity.BOTTOM));
  }

  private void initAction() {
    mBtnLoad.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mBtnLoad.setEnabled(false);
        mTvStatus.setText(getString(R.string.ad_start_loading));

        // Load Banner Ad
        AdRequest request = AdRequest.newBuilder().pub(PUB).build();
        mBannerAdView.loadAd(request);
      }
    });
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
    initView();
    initAction();
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
    public void onAdLoaded(Ad ad) {
      if (ad == mBannerAdView) {
        mTvStatus.setText(getString(R.string.ad_load_success));
      }
    }

    @Override
    public void onAdClosed(Ad ad) {
    }

    @Override
    public void onAdShowed(Ad ad) {
      if (ad == mBannerAdView) {
        mBtnLoad.setEnabled(true);
        mTvStatus.setText(getString(R.string.ad_showed));
      }
    }

    @Override
    public void onAdClicked(Ad ad) {
      if (ad == mBannerAdView) {
        Toast.makeText(getActivity(), getString(R.string.ad_clicked), Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    public void onAdError(Ad ad, AdError adError) {
      if (ad == mBannerAdView) {
        mTvStatus.setText(getString(R.string.ad_load_error, adError.getErrorMessage()));
      }
    }
  };
}
