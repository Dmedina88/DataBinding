package com.grayherring.databinding.data.provider;

import com.grayherring.databinding.data.DataProvider;
import com.grayherring.databinding.model.Book;
import io.realm.Realm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class BookAddedDataProvider extends DataProvider<Book, AddBookObserver> {

  public BookAddedDataProvider() {super(AddBookObserver.class);}

  public void addBook(Book book) {
    Observable.just(book).doOnNext(book1 -> {
      Realm realm = Realm.getDefaultInstance();
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
      data = book;
      getObservable().subscribe(data -> {
        for (AddBookObserver observer : observers) {
          observer.bookAdded(data);
        }
      });
    }).subscribeOn(rx.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }
}
