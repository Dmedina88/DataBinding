package com.grayherring.databinding.base;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

/**
 * Extend this ViewModel for your ViewModel implementations. Call {@link #attach(BaseView)} and
 * {@link #detach()} during the appropriate Activity/Fragment lifecycles. Implement {@link
 * #getEmptyView()} and set {@link #emptyView} to null or an anonymous class of your View.
 */
public abstract class BaseViewModel<T extends BaseView> extends BaseObservable {
  @NonNull protected T view;
  protected T emptyView;
  private final Class<T> viewClass;

  protected BaseViewModel(Class<T> viewClass) {
    this.viewClass = viewClass;
    emptyView = getEmptyView();
  }

  public void attach(final T view) {
    this.view = view;
  }

  public void detach() {
    this.view = emptyView;
  }

  public final Class<T> viewClass() {
    return viewClass;
  }

  /**
   * Initialize {@link #emptyView}. {@link #view} is set to {@link #emptyView} during {@link
   * #detach()}
   */
  protected abstract T getEmptyView();
}
