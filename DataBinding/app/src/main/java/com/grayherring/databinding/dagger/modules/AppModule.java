package com.grayherring.databinding.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.inputmethod.InputMethodManager;
import com.grayherring.databinding.SwagApp;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  private final SwagApp app;

  public AppModule(final SwagApp app) {
    this.app = app;
  }

  @Provides @PerApp SwagApp provideSwagApp() {
    return app;
  }

  @Provides @PerApp Application provideApplication() {
    return app;
  }

  @Provides @PerApp Resources provideResources(final Application app) {
    return app.getResources();
  }

  @Provides @PerApp InputMethodManager provideInputMethodManager(final Application app) {
    return (InputMethodManager) app.getSystemService(Context.INPUT_METHOD_SERVICE);
  }
}
