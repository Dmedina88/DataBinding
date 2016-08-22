package com.grayherring.databinding.dagger.component;

import com.grayherring.databinding.dagger.PerActivity;
import com.grayherring.databinding.dagger.modules.HomeModule;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(
    modules = HomeModule.class
)
public interface HomeComponent {

  //void inject(MainActivity mainActivity);
  //
  //void inject(MainVM VM);
  //
  //final class Initializer {
  //  private Initializer() {
  //    throw new AssertionError("No instances.");
  //  }
  //
  //  public static HomeComponent init(BaseActivity activity) {
  //    return DaggerHomeComponent.builder()
  //        .dataModule(new DataModule())
  //        .build();
  //  }
  //}
}