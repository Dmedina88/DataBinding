package com.grayherring.databinding.activity.main;

import android.databinding.ObservableArrayList;

import android.view.View;
import com.grayherring.databinding.base.BaseView;
import com.grayherring.databinding.base.BaseViewModel;
import com.grayherring.databinding.data.DataCenter;
import com.grayherring.databinding.model.Book;

import java.util.List;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by David on 6/4/2016.
 */

public class MainVM extends BaseViewModel<MainView> {
  ObservableArrayList<Book> books = new ObservableArrayList<>();

  protected MainVM(MainView view) {
    super(view);
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

  public void getAllData(View v){
    Timber.d("getALldata");
    DataCenter.getInstance().getAllData().subscribe(newBooks -> {
      books.clear();
      books.addAll(newBooks);
    });
  }

  public void startAddActivity(View v){
    view.startAddActivity();
  }


}
