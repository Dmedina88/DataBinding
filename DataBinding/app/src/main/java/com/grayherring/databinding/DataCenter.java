package com.grayherring.databinding;

import com.grayherring.databinding.model.Book;

import java.util.ArrayList;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
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

  public void deleteAllData() {
    Realm realm = Realm.getDefaultInstance();
    try {


    } catch (Exception e) {
      Timber.e(e.getLocalizedMessage());
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

  public void seed() {
    Realm realm = Realm.getDefaultInstance();
    realm.asObservable()
        .first()
        .observeOn(AndroidSchedulers.mainThread())
        .map(bgreal -> {
          Book book;
          ArrayList<Book> books = new ArrayList<>();
          for (int i = 0; i < 3; i++) {
            book = new Book();
            bgreal.beginTransaction();
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
            bgreal.copyToRealmOrUpdate(book);
            bgreal.commitTransaction();
            books.add(book);
          }
          bgreal.close();
          return books;
        });
  }

  //// TODO: 5/20/16 i should really create an error action
  public void add(Book book, String source) {
    Realm realm = Realm.getDefaultInstance();

//    realm.asObservable()
//        .first()
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(bgRealm -> {
//          bgRealm.beginTransaction();
//          try {
//            book.setId(realm.where(Book.class).max("id").intValue() + 1);
//            // no books yet
//          } catch (NullPointerException e) {
//            book.setId(0);
//          }
//          bgRealm.copyToRealmOrUpdate(book);
//          bgRealm.commitTransaction();
//          bgRealm.close();
//          SwagAction successAction = new SwagAction(ADD_BOOK);
//          successAction.source = source;
//          successAction.payload.books.add(book);
//          dispatcher.sendAction(successAction);
//        }, throwable -> {
//          Timber.e("add" + throwable.getLocalizedMessage());
//          realm.close();
//        }, () -> realm.close());
  }

  public void remove(final int position, final Book book,
      final String source) {
//    Realm realm = Realm.getDefaultInstance();
//    realm.where(Book.class).equalTo("id", book.getId()).findFirst().asObservable().subscribe(
//        realmObject -> {
//          realmObject.deleteFromRealm();
//          SwagAction successAction = new SwagAction(DELETE_BOOK);
//          successAction.source = source;
//          successAction.payload.pos = position;
//          successAction.payload.books.add(book);
//          dispatcher.sendAction(successAction);
//        }, throwable -> {
//          Timber.e("remove" + throwable.getLocalizedMessage());
//          realm.close();
//        }, realm::close);
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
