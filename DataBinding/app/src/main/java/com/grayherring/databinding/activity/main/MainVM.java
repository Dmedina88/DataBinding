package com.grayherring.databinding.activity.main;

import android.databinding.ObservableArrayList;
import android.view.View;
import com.grayherring.databinding.base.BaseViewModel;
import com.grayherring.databinding.data.DataCenter;
import com.grayherring.databinding.data.provider.AddBookObserver;
import com.grayherring.databinding.data.provider.NewListObserver;
import com.grayherring.databinding.model.Book;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by David on 6/4/2016.
 */

public class MainVM extends BaseViewModel<MainView> implements NewListObserver ,AddBookObserver {
  ObservableArrayList<Book> books = new ObservableArrayList<>();

  protected MainVM(final Class<MainView> viewClass) {
    super(viewClass);
  }

  public List<Book> getBooks() {
    return books;
  }

  public void seed() {
    DataCenter.getInstance().seed().subscribe(newBooks -> {
      books.addAll(newBooks);
    });
  }

  public void delete() {
    DataCenter.getInstance().deleteAllData();
    books.clear();
  }

  @Override protected MainView getEmptyView() {
    return new MainView() {
      @Override public void startAddActivity() {
      }

      @Override public void startDetailActivity() {
      }
    };
  }

  public void getAllData(View v) {
    DataCenter.getInstance().getAllData();
  }

  public void startAddActivity(View v) {
    view.startAddActivity();
  }

  @Override public void attach(MainView view) {
    super.attach(view);
    DataCenter.subscribe(this);
  }



  @Override public void detach() {
    DataCenter.unsubscribe(this);

    super.detach();
  }

  @Override public void bookAdded(Book book) {

  }

  @Override public void getNewList(List<Book> newBooks) {
    books.clear();
    books.addAll(newBooks);  }
}
