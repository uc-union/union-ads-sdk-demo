package com.ucweb.union.ads.sample;

import android.app.Application;

import com.ucweb.union.ads.UnionAdsSdk;

public class UnionAdsSdkSampleApp extends Application {
  @Override
  public void onCreate() {
    super.onCreate();

    UnionAdsSdk.start(this);
  }
}
