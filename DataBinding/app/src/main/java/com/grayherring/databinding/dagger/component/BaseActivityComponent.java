package com.grayherring.databinding.dagger.component;

import com.grayherring.databinding.base.BaseActivity;
import com.grayherring.databinding.dagger.Injector;
import com.grayherring.databinding.dagger.PerActivity;
import com.grayherring.databinding.dagger.modules.BaseActivityModule;
import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link PerActivity}
 */
@PerActivity
@Component(
    modules = BaseActivityModule.class,
    dependencies = appComponent.class
)
public interface BaseActivityComponent {

  final class Initializer {
    private Initializer() {
      throw new AssertionError("No instances.");
    }

    public static BaseActivityComponent init(BaseActivity activity) {
      return DaggerBaseActivityComponent.builder()
          .pPCareAppComponent(Injector.obtain(activity.getApplicationContext(), PPCareAppComponent.class))
          .baseActivityModule(new BaseActivityModule(activity))
          .build();
    }
  }
}
