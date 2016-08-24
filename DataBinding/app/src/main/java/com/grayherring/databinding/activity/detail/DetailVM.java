package com.grayherring.databinding.activity.detail;

import android.view.View;
import com.grayherring.databinding.base.BaseViewModel;
import com.grayherring.databinding.data.DataCenter;
import com.grayherring.databinding.model.Book;
import timber.log.Timber;

public class DetailVM extends BaseViewModel<DetailView> {

  private Book book;

  public DetailVM() {
  }

  public DetailVM(int id) {
    setBook(id);
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public void checkout(View v) {}

  @Override protected DetailView getEmptyView() {
    return new DetailView() {};
  }

  public Book getBook() {
    return book;
  }

  public void setBook(int id) {
    DataCenter.getInstance().getBookById(id).subscribe(book1 -> {
      Timber.d(book1.toString());
      book = book1;
      this.notifyChange();
    });
  }
}
