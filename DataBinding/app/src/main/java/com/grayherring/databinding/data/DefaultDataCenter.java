package com.grayherring.databinding.data;

import com.grayherring.databinding.model.Author;
import com.grayherring.databinding.model.Book;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by davidmedina on 4/22/16.
 */
public class DefaultDataCenter implements DataCenter {

  @Override public Observable<Boolean> deleteAllData() {
    final Realm realm = Realm.getDefaultInstance();

    return realm.asObservable().first().map(realm1 -> {
      Boolean value;
      try {
        realm1.beginTransaction();
        realm1.deleteAll();
        realm1.commitTransaction();
        value = true;
      } catch (Exception e) {
        Timber.e(e.getLocalizedMessage());
        value = false;
      } finally {
        realm1.close();
      }
      return value;
    });
  }

  @Override public Observable<ArrayList<Book>> seed() {
    final Random random = new Random();
    final ArrayList<Book> books = new ArrayList<>();
    return Observable.just(books).concatMap(books1 -> {

      Book book;
      Author author;
      Realm realm = Realm.getDefaultInstance();

      for (int i = 0; i < 7; i++) {
        book = new Book();
        realm.beginTransaction();
        try {
          book.setId(realm.where(Book.class).max("id").intValue() + 1);
          // no books yet
        } catch (NullPointerException e) {
          book.setId(0);
        }
        author = new Author();
        book.setTitle(new BigInteger(34, random).toString(6) + " index " + i + "id" + book.getId());
        author.setName("DAveHerring " + i);
        book.setAuthor(author);
        book.setPublisher("Grayherring inc");
        book.setCategories("fire");
        book.setImage("https://unsplash.it/600/600?image=" + random.nextInt(1000));

        books1.add(book);
        author.setBooks(books);
        realm.copyToRealmOrUpdate(book);
        realm.commitTransaction();
      }

      realm.close();
      return Observable.just(books1);
    }).subscribeOn(rx.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<Book> add(final Book book) {
    return Observable.just(book).map(book1 -> {
      Realm realm = Realm.getDefaultInstance();
      realm.beginTransaction();
      try {
        book1.setId(realm.where(Book.class).max("id").intValue() + 1);
        // no books yet
      } catch (NullPointerException e) {
        book1.setId(0);
      }
      realm.copyToRealmOrUpdate(book);
      realm.commitTransaction();
      realm.close();
      return book1;
    }).subscribeOn(rx.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<Book> remove(final Book book) {

    final Realm realm = Realm.getDefaultInstance();
    return realm.where(Book.class)
        .equalTo("id", book.getId())
        .findFirstAsync()
        .asObservable()
        .filter(Book::isLoaded)
        .concatMap(realmObject -> {
          {
            if (realmObject.isLoaded() && realmObject.isValid()) {
              realm.beginTransaction();
              realmObject.deleteFromRealm();
              realm.commitTransaction();
            }
            return Observable.just(book);
          }
        })
        .doOnError(e -> Timber.e(e.toString()));
  }

  @Override public Observable<List<Book>> getAllData() {
    final Realm realm = Realm.getDefaultInstance();
    return realm.where(Book.class).findAllAsync()
        .asObservable()
        .filter(RealmResults::isLoaded)
        .observeOn(AndroidSchedulers.mainThread())
        .map(realm::copyFromRealm)
        .doOnCompleted(realm::close);
  }

  @Override public Observable<Book> getBookById(int id) {
    final Realm realm = Realm.getDefaultInstance();
    final RealmObject realmObject = realm.where(Book.class).equalTo("id", id).findFirst();
    if (realmObject != null) {
      return realmObject.asObservable()
          .map(realmObject1 -> (Book) realmObject1)
          .map(realm::copyFromRealm)
          .observeOn(AndroidSchedulers.mainThread())
          .doOnCompleted(realm::close);
    } else {
      return Observable.empty();
    }
  }

  @Override public Observable<Book> update(final Book book) {
    final Realm realm = Realm.getDefaultInstance();
    return realm.asObservable().first()
        .observeOn(AndroidSchedulers.mainThread())
        .map(bgRealm -> {
          bgRealm.beginTransaction();
          bgRealm.copyToRealmOrUpdate(book);
          bgRealm.commitTransaction();
          bgRealm.close();
          return book;
        }).doOnError(this::logError).doOnCompleted(realm::close);
  }

  @Override public Observable<Book> checkOut(final Book book) {
    final Realm realm = Realm.getDefaultInstance();

    return realm.asObservable()
        .first()
        .observeOn(AndroidSchedulers.mainThread())
        .map(bgRealm -> {
          bgRealm.beginTransaction();
          DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
          Date today = Calendar.getInstance().getTime();
          String date = df.format(today);
          book.setLastCheckedOutBy("soy souse");
          book.setLastCheckedOut(date);
          bgRealm.copyToRealmOrUpdate(book);
          bgRealm.commitTransaction();
          bgRealm.close();
          return book;
        }).doOnCompleted(realm::close).doOnError(throwable -> {
          Timber.e("checkOut" + throwable.getLocalizedMessage());
        });
  }

  @Override public void addRealmChangeListener(RealmChangeListener<Realm> changeListener) {
    Realm.getDefaultInstance().addChangeListener(changeListener);
  }

  @Override public void removeRealmChangeListener(RealmChangeListener<Realm> changeListener) {
    Realm.getDefaultInstance().removeChangeListener(changeListener);
  }

  private void logError(final Throwable throwable) {
    Timber.d("##" + throwable.toString());
  }

  @Override public Observable<List<Book>> searchByTitle(String newText) {
    Realm realm = Realm.getDefaultInstance();
    return Realm.getDefaultInstance()
        .where(Book.class)
        .beginsWith("title", newText, Case.INSENSITIVE)
        .findAllAsync()
        .asObservable().observeOn(AndroidSchedulers.mainThread())
        .map(realm::copyFromRealm)
        .doOnCompleted(realm::close);
  }
}
