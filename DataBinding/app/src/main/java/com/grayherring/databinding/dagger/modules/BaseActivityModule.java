package com.grayherring.databinding.dagger.modules;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;

import com.grayherring.databinding.base.BaseActivity;
import com.grayherring.databinding.dagger.PerActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class BaseActivityModule {
  private final BaseActivity activity;

  public BaseActivityModule(BaseActivity activity) {
    this.activity = activity;
  }

  @Provides @PerActivity BaseActivity provideActivity() {
    return activity;
  }

  @Provides @PerActivity FragmentManager provideSupportFragmentManager() {
    return activity.getSupportFragmentManager();
  }

  @Provides @PerActivity Context provideContext() {
    return activity;
  }

  @Provides LinearLayoutManager provideLayoutManager() {
    return new LinearLayoutManager(activity);
  }
}