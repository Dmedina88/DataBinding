package com.grayherring.databinding.util;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class RxUtil {

  /**
   * Returns a boolean indicating whether a subscription is already being made
   */
  public static boolean inFlight(final Subscription subscription) {
    return subscription != null && !subscription.isUnsubscribed();
  }

  /**
   * UnSubscribe if the subscription is in flight
   */
  public static void unSubscribeIfNeeded(final Subscription subscription) {
    if (inFlight(subscription)) {
      subscription.unsubscribe();
    }
  }

  /**
   * UnSubscribe if the subscriptions are in flight
   */
  public static void unSubscribeIfNeeded(final Subscription... subscriptions) {
    for (final Subscription subscription : subscriptions) {
      unSubscribeIfNeeded(subscription);
    }
  }

  public static <T> Observable.Transformer<T, T> applySchedulers() {
    return observable -> observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

}