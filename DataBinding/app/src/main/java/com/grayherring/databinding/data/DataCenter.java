package com.grayherring.databinding.data;

import com.grayherring.databinding.model.Book;
import com.grayherring.databinding.model.RealmBook;
import com.grayherring.databinding.model.BookInterface;
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

  Observable<List<Book>> seed();

  //// TODO: 5/20/16 i should really create an error action
  Observable<BookInterface> add(BookInterface book);

  Observable<BookInterface> remove(BookInterface book);

  Observable<List<BookInterface>> getAllData();

  Observable<BookInterface> getBookById(int id);

  Observable<BookInterface> update(BookInterface book);

  Observable<BookInterface> checkOut(BookInterface book);

  void addRealmChangeListener(RealmChangeListener<Realm> changeListener);

  void removeRealmChangeListener(RealmChangeListener<Realm> changeListener);

  Observable<List<BookInterface>> searchByTitle(String newText);
}
