package com.grayherring.databinding.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.grayherring.databinding.BR;
import javax.inject.Inject;

public abstract class BaseBindingFragment<T extends ViewDataBinding,
    U extends BaseViewModel<V>,
    V extends BaseView>
    extends BaseFragment {
  @Inject protected U vm;

  protected T binding;

  /**
   * Override this method to customize the order of ViewBinding and ViewModel setup. By
   * default the order of calls:<ul>
   * <li>binding is assigned to {@link #setupBinding(LayoutInflater, ViewGroup, boolean)}</li>
   * <li>{@link #attachViewModel} is called to set up {@link this#vm vm}</li>
   * <li>{@link #attachViewModelToBinding()} sets the view model to the binding</li>
   * </ul>
   */
  @Nullable @Override public View onCreateView(
      LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = setupBinding(inflater, container, false);

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

    return binding.getRoot();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    detachViewModel();
  }

  /**
   * Override this method to customize view model attaching implementation. By default this gets
   * called during {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
   */
  protected void attachViewModel() {
    vm.attach((V) this);
  }

  /**
   * Override this method to customize view model detaching implementation. By default this gets
   * called during {@link #onDestroyView()}
   */
  protected void detachViewModel() {
    vm.detach();
  }

  /**
   * Override this method to customize viewmodel attachment to the view binding. By default this
   * gets called during {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
   */
  protected void attachViewModelToBinding() {
    binding.setVariable(BR.vm, vm);
  }

  /**
   * Override to provide a different {@link ViewDataBinding} object. By default it will inflate the
   * layout provided from {@link #getLayoutId()} Binding object can be accessed by subclasses
   * through {@link #binding this.binding}.
   */
  protected T setupBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
    return DataBindingUtil.inflate(inflater, getLayoutId(), container, attachToParent);
  }

  /**
   * Provide the parent layout ID for the fragment.
   * @return
   */
  protected abstract int getLayoutId();
}
