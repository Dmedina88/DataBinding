package com.grayherring.databinding.data.provider;

import com.grayherring.databinding.data.DataProvider;
import com.grayherring.databinding.model.RealmBook;
import com.grayherring.databinding.model.BookInterface;
import io.realm.Realm;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;

public class NewListDataProvider extends DataProvider<List<BookInterface>, NewListObserver> {

  public NewListDataProvider() {super(NewListObserver.class);}

  private void getAllBooks() {
    Realm realm = Realm.getDefaultInstance();
    realm.where(RealmBook.class).findAllAsync().asObservable()
        .observeOn(AndroidSchedulers.mainThread())
        .map(realm::copyFromRealm)
        .doOnCompleted(realm::close).subscribe(books -> {
      data = books;
      getObservable().subscribe(data -> {
        for (NewListObserver observer : observers) {
          observer.getNewList(data);
        }
      });
    });
  }
}
