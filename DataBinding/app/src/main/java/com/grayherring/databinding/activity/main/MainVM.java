package com.grayherring.databinding.activity.main;

import android.databinding.ObservableArrayList;
import android.view.View;
import com.grayherring.databinding.base.BaseViewModel;
import com.grayherring.databinding.data.DataCenter;
import com.grayherring.databinding.model.Book;
import java.util.List;

/**
 * Created by David on 6/4/2016.
 */

public class MainVM extends BaseViewModel<MainView> {

  ObservableArrayList<Book> books = new ObservableArrayList<>();

  public MainVM() {
    super();
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
    DataCenter.getInstance().deleteAllData().subscribe(value -> {
      //// TODO: 8/20/16  maybe do something with this lol
      if (value == true) {
        books.clear();
      }
    });
  }

  @Override protected MainView getEmptyView() {
    return new MainView() {
      @Override public void startAddActivity() {
      }

      @Override public void startDetailActivity(int position) {
      }
    };
  }

  public void getAllData(View v) {
    DataCenter.getInstance().getAllData().subscribe(books1 -> {
      MainVM.this.books.clear();
      MainVM.this.books.addAll(books1);
    });
  }

  public void startAddActivity(View v) {
    view.startAddActivity();
  }

  @Override public void attach(MainView view) {
    super.attach(view);
  }

  @Override public void detach() {
    super.detach();
  }

  public void startDetailView(int position) {
    view.startDetailActivity(position);
  }
}
