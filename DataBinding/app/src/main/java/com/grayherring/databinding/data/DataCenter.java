package com.grayherring.databinding.data;

import com.grayherring.databinding.model.Book;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
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
public class DataCenter {

  private static DataCenter instance;

  public static DataCenter init(DataCenter dataCenter) {
    if (instance == null) {
      synchronized (DataCenter.class) {
        if (instance == null) {
          instance = dataCenter;
        }
      }
    }
    return instance;
  }

  public static DataCenter init(List<DataProvider> dataProviders) {
    if (instance == null) {
      synchronized (DataCenter.class) {
        if (instance == null) {
          instance = new DataCenter();
        }
      }
    }
    return instance;
  }

  public static DataCenter getInstance() {
    if (instance == null) {
      throw new InstantiationError(
          "DataManager was never set up. Call DataManager.init(dataProviders)");
    }
    return instance;
  }

  public Observable<Boolean> deleteAllData() {
    Realm realm = Realm.getDefaultInstance();
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

  public Observable<ArrayList<Book>> seed() {
    Random random = new Random();
    ArrayList<Book> books = new ArrayList<>();
    return Observable.just(books).concatMap(books1 -> {
      Book book;
      Realm realm = Realm.getDefaultInstance();
      for (int i = 0; i < 200; i++) {
        book = new Book();
        realm.beginTransaction();
        try {
          book.setId(realm.where(Book.class).max("id").intValue() + 1);
          // no books yet
        } catch (NullPointerException e) {
          book.setId(0);
        }
        book.setTitle(new BigInteger(34, random).toString(6) + " index " + i + "id" + book.getId());
        book.setAuthor("Grayherring BattleStar");
        book.setPublisher("Grayherring inc");
        book.setCategories("fire");
        book.setImage("https://unsplash.it/600/600?image=" + random.nextInt(1000));
        realm.copyToRealmOrUpdate(book);
        realm.commitTransaction();
        books1.add(book);
      }

      realm.close();
      return Observable.just(books1);
    }).subscribeOn(rx.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }

  //// TODO: 5/20/16 i should really create an error action
  public Observable<Book> add(final Book book) {
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

  public Observable<Book> remove(final Book book) {

    Realm realm = Realm.getDefaultInstance();
    return realm.where(Book.class)
        .equalTo("id", book.getId())
        .findFirstAsync()
        .asObservable()
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

  public Observable<List<Book>> getAllData() {
    Realm realm = Realm.getDefaultInstance();
    return realm.where(Book.class).findAllAsync().asObservable()
        .observeOn(AndroidSchedulers.mainThread())
        .map(realm::copyFromRealm)
        .doOnCompleted(realm::close);
  }

  public Observable<Book> getBookById(int id) {
    Realm realm = Realm.getDefaultInstance();
    RealmObject realmObject = realm.where(Book.class).equalTo("id", id).findFirst();
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

  public Observable<Book> update(final Book book) {
    Realm realm = Realm.getDefaultInstance();
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

  public Observable<Book> checkOut(final Book book) {
    Realm realm = Realm.getDefaultInstance();

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

  public void addRealmChangeListener(RealmChangeListener<Realm> changeListener) {
    Realm.getDefaultInstance().addChangeListener(changeListener);
  }

  public void removeRealmChangeListener(RealmChangeListener<Realm> changeListener) {
    Realm.getDefaultInstance().removeChangeListener(changeListener);
  }

  private void logError(Throwable throwable) {
    Timber.d("##" + throwable.toString());
  }

  public Observable<List<Book>> searchByTitle(String newText) {
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
