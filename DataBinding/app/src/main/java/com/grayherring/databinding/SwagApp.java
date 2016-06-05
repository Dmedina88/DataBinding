package com.grayherring.databinding;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by David on 4/23/2016.
 */
public class SwagApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
    Realm.setDefaultConfiguration(new RealmConfiguration.Builder(getApplicationContext()).build());

  }
}
