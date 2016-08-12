package com.grayherring.databinding.dagger.component;

import com.grayherring.databinding.activity.main.MainActivity;
import com.grayherring.databinding.base.BaseActivity;
import com.grayherring.databinding.dagger.PerActivity;
import com.grayherring.databinding.dagger.modules.DataModule;
import dagger.Component;

@PerActivity
@Component(
    modules = DataModule.class
    )
public interface HomeComponent {

   void inject(MainActivity mainActivity);

  final class Initializer {
    private Initializer() {
      throw new AssertionError("No instances.");
    }

    public static HomeComponent init(BaseActivity activity) {
      return DaggerHomeComponent.builder()
          .dataModule(new DataModule(activity.getBaseContext()))
          .build();
    }
  }
}