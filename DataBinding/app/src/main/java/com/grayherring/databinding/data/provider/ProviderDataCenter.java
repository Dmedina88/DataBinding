package com.grayherring.databinding.data.provider;

import android.support.annotation.NonNull;
import com.grayherring.databinding.data.DataProvider;
import com.grayherring.databinding.model.Book;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by davidmedina on 4/22/16.
 */
public class ProviderDataCenter {

  private static ProviderDataCenter instance;
  private final Observable<DataProvider> dataProviders;

  private ProviderDataCenter(List<DataProvider> dataProviders) {
    this.dataProviders = Observable.from(dataProviders);
  }

  public static ProviderDataCenter init(List<DataProvider> dataProviders) {
    if (instance == null) {
      synchronized (ProviderDataCenter.class) {
        if (instance == null) {
          instance = new ProviderDataCenter(dataProviders);
        }
      }
    }
    return instance;
  }

  public static ProviderDataCenter getInstance() {
    if (instance == null) {
      throw new InstantiationError(
          "DataManager was never set up. Call DataManager.init(dataProviders)");
    }
    return instance;
  }

  public boolean deleteAllData() {
    Realm realm = Realm.getDefaultInstance();
    try {
      realm.deleteAll();
      return true;
    } catch (Exception e) {
      Timber.e(e.getLocalizedMessage());
      return false;
    } finally {
      realm.close();
    }
  }

  //  public void register(
  //
  //  }
  //
  //  public void unRegister(
  //
  //  }public String getImage(){

  public Observable<ArrayList<Book>> seed() {
    Random random = new Random();
    ArrayList<Book> books = new ArrayList<>();
    return Observable.just(books).concatMap(books1 -> {
      Book book;
      Realm realm = Realm.getDefaultInstance();
      for (int i = 0; i < 3; i++) {
        book = new Book();
        realm.beginTransaction();
        try {
          book.setId(realm.where(Book.class).max("id").intValue() + 1);
          // no books yet
        } catch (NullPointerException e) {
          book.setId(0);
        }
        book.setTitle("Flux Book V" + i);
        book.setAuthor("Grayherring BattleStar");
        book.setPublisher("Grayherring inc");
        book.setCategories("fire");
        book.setImage("https://unsplash.it/600/600?image=" + random.nextInt(1000));
        realm.copyToRealmOrUpdate(book);
        realm.commitTransaction();
        books1.add(book);
      }
      realm.close();
      Observable<ArrayList<Book>> booksObservable = Observable.just(books1);
      return booksObservable;
    }).subscribeOn(rx.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }

  //// TODO: 5/20/16 i should really create an error action
  public void add(final Book book) {
    provider(BookAddedDataProvider.class).subscribe(bookAddedDataProvider -> {
      bookAddedDataProvider.addBook(book);
    });
  }

  public Observable<Book> remove(final Book book) {
    return Observable.just(book).doOnNext(book1 -> {
      Realm realm = Realm.getDefaultInstance();
      realm.where(Book.class)
          .equalTo("id", book.getId())
          .findFirstAsync()
          .asObservable()
          .concatMap(realmObject -> {
            {
              realmObject.deleteFromRealm();
              return Observable.just(book);
            }
          })
          .doOnError(e -> realm.close());
    }).observeOn(AndroidSchedulers.mainThread());
  }

  public void getAllData() {
    provider(NewListDataProvider.class).subscribe(newListDataProvider -> {
      newListDataProvider.getData();
    });
  }

  public void update(final Integer position,
      final Book book, final String source) {
    //
    //    Realm realm = Realm.getDefaultInstance();
    //    realm.asObservable()
    //        .observeOn(AndroidSchedulers.mainThread())
    //        .subscribe(bgRealm -> {
    //          bgRealm.beginTransaction();
    //          bgRealm.copyToRealmOrUpdate(book);
    //          bgRealm.commitTransaction();
    //          bgRealm.close();
    //          SwagAction successAction = new SwagAction(UPDATE_BOOK);
    //          successAction.source = source;
    //          successAction.payload.pos = position;
    //          successAction.payload.books.add(book);
    //          dispatcher.sendAction(successAction);
    //        }, throwable -> {
    //          Timber.e("update" + throwable.getLocalizedMessage());
    //          realm.close();
    //        }, realm::close);
  }
/*
  public void checkOut(final Integer position, final Book book, final String source) {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Realm realm = Realm.getDefaultInstance();
    Date today = Calendar.getInstance().getTime();
    String date = df.format(today);
    book.setLastCheckedOutBy("soy souse");
    book.setLastCheckedOut(date);

    realm.asObservable()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(bgRealm -> {
          bgRealm.beginTransaction();
          bgRealm.copyToRealmOrUpdate(book);
          bgRealm.commitTransaction();
          bgRealm.close();
          SwagAction successAction = new SwagAction(UPDATE_BOOK);
          successAction.source = source;
          successAction.payload.pos = position;
          successAction.payload.books.add(book);
          dispatcher.sendAction(successAction);
        }, throwable -> {
          Timber.e("checkOut" + throwable.getLocalizedMessage());
          realm.close();
        }, realm::close);
  }

  public void filter(final String filter) {
    SwagAction filterAction = new SwagAction(FILTER);
    filterAction.payload.filter = filter;
    dispatcher.sendAction(filterAction);
  }

  public void undoFilter() {
    SwagAction filterAction = new SwagAction(FILTER_UNDO);
    dispatcher.sendAction(filterAction);
  }

  public void selectBook(Book book, int index, String source) {
    SwagAction selectBook = new SwagAction(SELECT_BOOK);
    selectBook.source = source;

    selectBook.payload.books.add(book);
    selectBook.payload.pos = index;
    dispatcher.sendAction(selectBook);
  }

  public void startActivity(Intent intent, String source) {
    SwagAction activityAction = new SwagAction(START_ACTIVITY);
    activityAction.source = source;
    activityAction.payload.intent = intent;
    dispatcher.sendAction(activityAction);
  }

  public void backPress(String s) {
    SwagAction backPress = new SwagAction(BACK_PRESS);
    backPress.source = s;
    dispatcher.sendAction(backPress);
  }



  private void failure(Throwable throwable) {
    Timber.d(throwable.getLocalizedMessage());
    SwagAction failAction = new SwagAction(DATA_FAIL);
    dispatcher.sendAction(failAction);
  }

  private void success(List<Book> books) {
    SwagAction successAction = new SwagAction(DATA_CHANGE_NEW);
    successAction.payload.books.addAll(books);
    dispatcher.sendAction(successAction);
  }
  */

  //public static void subscribe(DataObserver observer) {
  //  instance.dataProviders.filter(provider -> provider.canObserve(observer))
  //      .subscribe(provider -> provider.subscribe(observer), throwable -> {
  //        Timber.e(throwable.getMessage());
  //      });
  //}
  //
  //public static void unsubscribe(DataObserver observer) {
  //  instance.dataProviders.filter(provider -> provider.canObserve(observer))
  //      .subscribe(provider -> provider.unSubscribe(observer),
  //          throwable -> {
  //            Timber.e(throwable.getMessage());
  //          });
  //}

  @NonNull private <T extends DataProvider> Observable<T> provider(Class<T> clazz) {
    return dataProviders.filter(clazz::isInstance).map(clazz::cast);
  }
}
