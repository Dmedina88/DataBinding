package com.grayherring.databinding.dagger.component;

import android.app.Application;
import com.grayherring.databinding.SwagApp;
import com.grayherring.databinding.dagger.PerApp;
import com.grayherring.databinding.dagger.modules.DataModule;
import com.grayherring.databinding.dagger.modules.HomeModule;
import com.grayherring.databinding.dagger.modules.AppModule;
import dagger.Component;

@PerApp // Constraints this component to one-per-application or unscoped bindings.
@Component(
    modules = {
        AppModule.class,
        DataModule.class
    })
public interface AppComponent {

  AppComponent plus(AppModule module);
  HomeComponent plus(HomeModule module);


  void inject(SwagApp baseApplication);

  final class Initializer {
    public static AppComponent init(final SwagApp app) {
      return DaggerBaseAppComponent.builder()
          .appModule(new AppModule(app))
          .dataModule(new DataModule())
          .build();
    }

    private Initializer() {
      throw new AssertionError("No instances.");
    }
  }
}