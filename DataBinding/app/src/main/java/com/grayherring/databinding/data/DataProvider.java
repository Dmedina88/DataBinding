package com.grayherring.databinding.data;

import android.support.annotation.NonNull;
import java.util.Set;
import rx.Observable;

public abstract class DataProvider<S, T extends DataObserver> {

  public final Class<T> observerType;
  protected S data;
  protected Set<T> observers;
  // protected Observable<S> dataObservable;

  protected DataProvider(Class<T> observerType) {
    this.observerType = observerType;
  }

  protected Observable<S> getObservable() {
    return Observable.just(data);
  }

  protected void subscribe(@NonNull T observer) {
    this.observers.add(observer);
  }

  protected void unSubscribe(@NonNull T observer) {
    synchronized (observers) {
      observers.remove(observer);
    }
  }

  boolean canObserve(DataObserver observer) {
    return this.observerType.isInstance(observer);
  }

  public S getData(){
    return data;
  }

  public void setData(S data) {
    this.data = data;
  }


}
