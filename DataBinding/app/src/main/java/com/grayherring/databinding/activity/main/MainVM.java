package com.grayherring.databinding.activity.main;

import android.databinding.ObservableArrayList;
import android.view.View;
import com.grayherring.databinding.base.BaseViewModel;
import com.grayherring.databinding.data.SwagDataCenter;
import com.grayherring.databinding.model.Book;
import com.grayherring.databinding.util.RxUtil;
import java.util.List;
import rx.Subscription;

/**
 * Created by David on 6/4/2016.
 */

public class MainVM extends BaseViewModel<MainView> {

  ObservableArrayList<Book> books = new ObservableArrayList<>();
  private Subscription dataSubscription;

  public MainVM() {
    super();
  }

  public List<Book> getBooks() {
    return books;
  }

  public void seed() {
    SwagDataCenter.getInstance().seed().subscribe(newBooks -> {
      books.addAll(newBooks);
    });
  }

  public void delete() {
    SwagDataCenter.getInstance().deleteAllData().subscribe(value -> {
      if (value) {
        books.clear();
      }
    });
  }

  @Override protected MainView getEmptyView() {
    return new MainView() {
      @Override public void startAddActivity() {
      }

      @Override public void startDetailActivity(final int position) {
      }
    };
  }

  public void getAllData(final View v) {
    RxUtil.unSubscribeIfNeeded(dataSubscription);
    dataSubscription = SwagDataCenter.getInstance().getAllData().subscribe(books1 -> {
          MainVM.this.books.clear();
          MainVM.this.books.addAll(books1);
        }
    );
  }

  public void startAddActivity(final View v) {
    view.startAddActivity();
  }

  @Override public void attach(final MainView view) {
    super.attach(view);
  }

  @Override public void detach() {
    super.detach();
    RxUtil.unSubscribeIfNeeded(dataSubscription);
  }

  public void startDetailView(final int position) {
    view.startDetailActivity(position);
  }

  public void search(final String newText) {
    RxUtil.unSubscribeIfNeeded(dataSubscription);
    dataSubscription = SwagDataCenter.getInstance().searchByTitle(newText).subscribe(
        books1 -> {
          MainVM.this.books.clear();
          MainVM.this.books.addAll(books1);
        });
  }
}
