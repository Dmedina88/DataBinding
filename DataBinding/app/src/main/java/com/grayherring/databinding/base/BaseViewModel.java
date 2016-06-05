package com.grayherring.databinding.base;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

/**
 * Created by David on 6/5/2016.
 */
public abstract class BaseViewModel<T extends BaseView> extends BaseObservable {
  @NonNull
  protected T view;
  protected T emptyView;
  private final Class<T> viewClass;

  protected BaseViewModel(Class<T> viewClass) {
    this.viewClass = viewClass;
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