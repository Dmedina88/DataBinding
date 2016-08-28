package com.grayherring.databinding.data;

import com.grayherring.databinding.model.Book;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;

/**
 * Created by davidmedina on 8/26/16.
 */
public interface DataCenter {
  Observable<Boolean> deleteAllData();

  Observable<ArrayList<Book>> seed();

  //// TODO: 5/20/16 i should really create an error action
  Observable<Book> add(Book book);

  Observable<Book> remove(Book book);

  Observable<List<Book>> getAllData();

  Observable<Book> getBookById(int id);

  Observable<Book> update(Book book);

  Observable<Book> checkOut(Book book);

  void addRealmChangeListener(RealmChangeListener<Realm> changeListener);

  void removeRealmChangeListener(RealmChangeListener<Realm> changeListener);

  Observable<List<Book>> searchByTitle(String newText);
}
