package com.grayherring.databinding.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import com.grayherring.databinding.BR;
import javax.inject.Inject;

public abstract class BaseBindingActivity<T extends ViewDataBinding, U extends BaseViewModel<V>, V extends BaseView>
    extends BaseActivity {
  @Inject protected U vm;

  protected T binding;

  /**
   * Override this method to customize the order of ViewBinding and ViewModel setup. By
   * default the order of calls:<ul>
   * <li>binding is assigned to {@link #setupBinding()}</li>
   * <li>{@link #attachViewModel} is called to set up {@link this#vm vm}</li>
   * <li>{@link #attachViewModelToBinding()} sets the view model to the binding</li>
   * </ul>
   */
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = setupBinding();

    try {
      attachViewModel();
    } catch (ClassCastException e) {
      throw new ClassCastException(String.format(
          "%s must implement interface %s",
          this.getClass().getSimpleName(),
          vm.viewClass().getSimpleName()
      ));
    }

    attachViewModelToBinding();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    detachViewModel();
  }

  /**
   * Override this method to customize view model attaching implementation. By default this gets
   * called during {@link #onCreate(Bundle)}
   */
  protected void attachViewModel() {
    vm.attach((V) this);
  }

  /**
   * Override this method to customize view model detaching implementation. By default this gets
   * called during {@link #onDestroy()}
   */
  protected void detachViewModel() {
    vm.detach();
  }

  /**
   * Override this method to customize viewmodel attachment to the view binding. By default this
   * gets called during {@link #onCreate(Bundle)}
   */
  protected void attachViewModelToBinding() {
    binding.setVariable(BR.vm, vm);
  }

  /**
   * Override to provide a different {@link ViewDataBinding} object. By default it will inflate the
   * layout provided from {@link #getLayoutId()} Binding object can be accessed by subclasses
   * through {@link #binding this.binding}.
   */
  protected T setupBinding() {
    return DataBindingUtil.setContentView(this, getLayoutId());
  }

  protected abstract int getLayoutId();
}
