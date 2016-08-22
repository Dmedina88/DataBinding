package com.grayherring.databinding.base;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Base activity created to be extended by every activity.
 * This class provides dependency injection configuration, ButterKnife configuration and
 * some methods common to every activity.
 */
public abstract class BaseActivity extends RxAppCompatActivity implements ErrorHandler {

  @Override protected void onCreate(Bundle savedInstanceState) {
    initializeDependencyInjector();
    super.onCreate(savedInstanceState);
  }

  @Override protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override public boolean onKeyUp(int keyCode, KeyEvent event) {
    // this is used to open the debug activity in debug builds
    return super.onKeyUp(keyCode, event);
  }

  /**
   * Initialize and inject your graph component, if needed.
   */
  protected abstract void initializeDependencyInjector();

  @Override public void logError(Throwable error) {
    Timber.e(error.getLocalizedMessage());
  }
}