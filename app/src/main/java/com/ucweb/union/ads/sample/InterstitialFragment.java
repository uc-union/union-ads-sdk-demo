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
import com.ucweb.union.ads.InterstitialAd;
import com.ucweb.union.ads.UnionAd;

public class InterstitialFragment extends Fragment {
  private InterstitialAd mInterstitialAd;
  private FrameLayout mContentContainer;
  private Button mBtnLoad;
  private Button mBtnShow;
  private TextView mTvStatus;

  /**
   * You should use your own **PUB** in production
   */
  private static final String PUB = "ssr@debuginterstitial";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mInterstitialAd = new InterstitialAd(getActivity());
    mInterstitialAd.setAdListener(mAdListener);

    initView();
    initAction();
  }

  private void initView() {
    mBtnLoad = new Button(getActivity());
    mBtnLoad.setText(getString(R.string.load));

    mBtnShow = new Button(getActivity());
    mBtnShow.setText(getString(R.string.show));
    mBtnShow.setVisibility(View.INVISIBLE);

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
        mInterstitialAd.loadAd(request);

        mTvStatus.setVisibility(View.VISIBLE);
      }
    });

    mBtnShow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mBtnShow.setVisibility(View.INVISIBLE);
        mInterstitialAd.show();
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
    mContentContainer.addView(mBtnShow,
                              new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                           FrameLayout.LayoutParams.WRAP_CONTENT,
                                                           Gravity.CENTER));

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
    mInterstitialAd = null;
    super.onDestroy();
  }

  private final AdListener mAdListener = new AdListener() {
    @Override
    public void onAdLoaded(UnionAd unionAd) {
      if (unionAd == mInterstitialAd) {
        mTvStatus.setText(getString(R.string.ad_load_success));
        mBtnShow.setVisibility(View.VISIBLE);
      }
    }

    @Override
    public void onAdClosed(UnionAd unionAd) {
      if (unionAd == mInterstitialAd) {
        mTvStatus.setText(getString(R.string.ad_closed));
      }
    }

    @Override
    public void onAdShowed(UnionAd unionAd) {
      if (unionAd == mInterstitialAd) {
        mTvStatus.setText(getString(R.string.ad_showed));
      }
    }

    @Override
    public void onAdClicked(UnionAd unionAd) {
      if (unionAd == mInterstitialAd) {
        Toast.makeText(getActivity(), getString(R.string.ad_clicked), Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    public void onAdError(UnionAd unionAd, AdError adError) {
      if (unionAd == mInterstitialAd) {
        mTvStatus.setText(getString(R.string.ad_load_error, adError.getErrorMessage()));
      }
    }
  };
}
