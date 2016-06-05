package com.grayherring.databinding.base;

import com.grayherring.databinding.model.Book;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by davidmedina on 4/22/16.
 */
//// TODO: I had these all as static at one point but i changed I liked the idea of maybe impmenting more the one kind of action Creater
public class DataCenter {

  private DataCenter() {
  }

  private static DataCenter instance;

  public static DataCenter getInstance() {
    if (instance == null) {
      instance = new DataCenter();
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
//  }

  public Observable<ArrayList<Book>> seed() {

    ArrayList<Book> books = new ArrayList<>();
    Realm realm = Realm.getDefaultInstance();
    return Observable.just(books).concatMap(books1 -> {
      Book book;
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
        book.setAuthor("Grayherring inc");
        book.setPublisher("Grayherring inc");
        book.setCategories("fire");
        realm.copyToRealmOrUpdate(book);
        realm.commitTransaction();
        books1.add(book);
      }
      realm.close();
      Observable<ArrayList<Book>> booksObservable = rx.Observable.just(books1);
      return booksObservable;
    }).subscribeOn(rx.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnError(e -> realm.close());

  }

  //// TODO: 5/20/16 i should really create an error action
  public Observable<Book> add(final Book book) {
    Realm realm = Realm.getDefaultInstance();
    return Observable.just(book).doOnNext(book1 -> {
      realm.beginTransaction();
      try {
        book.setId(realm.where(Book.class).max("id").intValue() + 1);
        // no books yet
      } catch (NullPointerException e) {
        book.setId(0);
      }
      realm.copyToRealmOrUpdate(book);
      realm.commitTransaction();
      realm.close();
    }).subscribeOn(rx.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnError(e -> realm.close());

  }

  public Observable<Book> remove(final Book book) {
    Realm realm = Realm.getDefaultInstance();
    return Observable.just(book).doOnNext(book1 -> {
      realm.where(Book.class).equalTo("id", book.getId()).findFirstAsync().asObservable().concatMap((Func1<RealmObject, Observable<Book>>) realmObject -> {
        {
          realmObject.deleteFromRealm();
          return Observable.just(book);
        }
      }).doOnError(e -> realm.close());
    }).observeOn(AndroidSchedulers.mainThread());
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

  public void getAllData() {
    Realm realm = Realm.getDefaultInstance();
    realm.where(Book.class).findAllAsync().asObservable()
        .observeOn(AndroidSchedulers.mainThread())
        .map(realm::copyFromRealm)
        .doOnNext(this::success)
        .doOnError(this::failure)
        .doOnCompleted(realm::close)
        .subscribe();
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
}
