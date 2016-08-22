package com.grayherring.databinding.dagger.component;

import com.grayherring.databinding.SwagApp;
import com.grayherring.databinding.dagger.PerApp;
import com.grayherring.databinding.dagger.modules.AppModule;
import com.grayherring.databinding.dagger.modules.DataModule;
import com.grayherring.databinding.dagger.modules.HomeModule;
import dagger.Component;

@PerApp // Constraints this component to one-per-application or unscoped bindings.
@Component(
    modules = {
        AppModule.class,
        DataModule.class,
    })
public interface AppComponent {

  HomeComponent plus(HomeModule module);

  void inject(SwagApp baseApplication);

  final class Initializer {
    private Initializer() {
      throw new AssertionError("No instances.");
    }

    public static AppComponent init(final SwagApp app) {
      return DaggerAppComponent.builder()
          .appModule(new AppModule(app))
          .dataModule(new DataModule())
          .build();
    }
  }
}