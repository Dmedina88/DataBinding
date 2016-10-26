package com.grayherring.databinding.data;

import com.grayherring.databinding.model.Book;
import com.grayherring.databinding.model.BookInterface;
import com.grayherring.databinding.model.RealmBook;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static com.grayherring.databinding.util.RxUtil.applySchedulers;

/**
 * Created by davidmedina on 4/22/16.
 */
public class RealmDataCenter implements DataCenter {

  @Override public Observable<Boolean> deleteAllData() {
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

  @Override public Observable<List<Book>> seed() {
    Random random = new Random();
    ArrayList<BookInterface> books = new ArrayList<>();
    return Observable.just(books).concatMap(books1 -> {
      BookInterface book;
      Realm realm = Realm.getDefaultInstance();
      for (int i = 0; i < 200; i++) {
        book = new RealmBook();
        realm.beginTransaction();
        try {
          book.setId(realm.where(RealmBook.class).max("id").intValue() + 1);
          // no books yet
        } catch (NullPointerException e) {
          book.setId(0);
        }
        book.setTitle(new BigInteger(34, random).toString(6) + " index " + i + "id" + book.getId());;
        book.setPublisher("Grayherring inc");
        book.setCategories("fire");
        book.setImage("https://unsplash.it/600/600?image=" + random.nextInt(1000));

        books1.add(book);
        realm.commitTransaction();
      }

      realm.close();
      return Observable.from(books1).cast(RealmBook.class).map(Book::new).toList();
    }).compose(applySchedulers());
  }

  //// TODO: 5/20/16 i should really create an error action
  @Override public Observable<BookInterface> add(final BookInterface realmBook) {
    return Observable.just(realmBook).cast(RealmBook.class).map(
        book -> {
          Realm realm = Realm.getDefaultInstance();
          realm.beginTransaction();
          try {
            book.setId(realm.where(RealmBook.class).max("id").intValue() + 1);
            // no books yet
          } catch (NullPointerException e) {
            book.setId(0);
          }
          realm.copyToRealmOrUpdate(book);
          realm.commitTransaction();
          realm.close();
          return new Book(book);
        }).compose(applySchedulers());
  }

  @Override public Observable<BookInterface> remove(final BookInterface book) {

    Realm realm = Realm.getDefaultInstance();
    return realm.where(RealmBook.class)
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

  @Override public Observable<List<BookInterface>> getAllData() {
    Realm realm = Realm.getDefaultInstance();
    return realm.where(RealmBook.class).findAllAsync().asObservable()
        .observeOn(AndroidSchedulers.mainThread())
        .map(stuff -> {
              List<BookInterface> list = new ArrayList<>();
              list.addAll(stuff);
              return list;
            }
        )
        .doOnCompleted(realm::close);
  }

  @Override public Observable<BookInterface> getBookById(int id) {
    Realm realm = Realm.getDefaultInstance();
    RealmObject realmObject = realm.where(RealmBook.class).equalTo("id", id).findFirst();
    if (realmObject != null) {
      return realmObject.asObservable()
          .map(realmObject1 -> (BookInterface) realmObject1)
          .observeOn(AndroidSchedulers.mainThread())
          .doOnCompleted(realm::close);
    } else {
      return Observable.empty();
    }
  }

  @Override public Observable<BookInterface> update(final BookInterface book) {
    // Realm realm = Realm.getDefaultInstance();
    //return realm.asObservable().first()
    //    .compose(applySchedulers())
    //    .map(bgRealm -> {
    //      //bgRealm.beginTransaction();
    //      //bgRealm.copyToRealmOrUpdate(book);
    //      //bgRealm.commitTransaction();
    //      //bgRealm.close();
    //      return book;
    //    }).doOnError(this::logError).doOnCompleted(realm::close);
    return Observable.just(book);
  }

  @Override public Observable<BookInterface> checkOut(final BookInterface book) {
    //Realm realm = Realm.getDefaultInstance();
    //return realm.asObservable()
    //    .first()
    //    .observeOn(AndroidSchedulers.mainThread())
    //    .map(bgRealm -> {
    //      // bgRealm.beginTransaction();
    //      DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    //      Date today = Calendar.getInstance().getTime();
    //      String date = df.format(today);
    //      book.setLastCheckedOutBy("soy souse");
    //      book.setLastCheckedOut(date);
    //      //bgRealm.copyToRealmOrUpdate(book);
    //      //bgRealm.commitTransaction();
    //      //bgRealm.close();
    //      return new Book(book);
    //    }).doOnCompleted(realm::close).doOnError(throwable -> {
    //      Timber.e("checkOut" + throwable.getLocalizedMessage());
    //    });
    return Observable.just(book);
  }

  @Override public void addRealmChangeListener(RealmChangeListener<Realm> changeListener) {
    Realm.getDefaultInstance().addChangeListener(changeListener);
  }

  @Override public void removeRealmChangeListener(RealmChangeListener<Realm> changeListener) {
    Realm.getDefaultInstance().removeChangeListener(changeListener);
  }

  private void logError(Throwable throwable) {
    Timber.d("##" + throwable.toString());
  }

  @Override public Observable<List<BookInterface>> searchByTitle(String newText) {
    Realm realm = Realm.getDefaultInstance();
    return Realm.getDefaultInstance()
        .where(RealmBook.class)
        .beginsWith("title", newText, Case.INSENSITIVE)
        .findAllAsync()
        .asObservable().observeOn(AndroidSchedulers.mainThread())
        .map(stuff -> {
              List<BookInterface> list = new ArrayList<>();
              list.addAll(stuff);
              return list;
            }
        )
        .doOnCompleted(realm::close);
  }
}
