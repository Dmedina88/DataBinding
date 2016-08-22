package com.grayherring.databinding.dagger.modules;

import android.app.Activity;
import android.content.Context;
import com.grayherring.databinding.activity.main.MainActivity;
import com.grayherring.databinding.activity.main.MainVM;
import com.grayherring.databinding.dagger.PerActivity;
import dagger.Module;
import dagger.Provides;

@Module public class HomeModule {

  private final MainActivity activity;

  public HomeModule(final MainActivity activity) {
    this.activity = activity;
  }

  @Provides @PerActivity Context provideContext() {
    return activity;
  }

  @Provides @PerActivity Activity provideActivity() {
    return activity;
  }

  @Provides @PerActivity MainVM provideMainVM() {return new MainVM();}
}
