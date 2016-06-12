package com.grayherring.databinding.base;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

/**
 * Created by David on 6/5/2016.
 */
public abstract class BaseViewModel<T extends BaseView> extends BaseObservable {
  @NonNull protected T view;
  protected T emptyView;

  protected BaseViewModel(T view) {
    this.view =view;
    this.emptyView = getEmptyView();
  }

  public void attach(final T view) {
    this.view = view;
  }

  public void detach() {
    this.view = emptyView;
  }


  /**
   * Initialize {@link #emptyView}. {@link #view} is set to {@link #emptyView} during {@link
   * #detach()}
   */
  protected abstract T getEmptyView();
}
