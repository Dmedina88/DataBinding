package com.grayherring.databinding.activity.detail;

import android.view.View;
import com.grayherring.databinding.base.BaseViewModel;
import com.grayherring.databinding.data.DataCenter;
import com.grayherring.databinding.model.Book;
import com.grayherring.databinding.util.RxUtil;
import rx.Subscription;

public class DetailVM extends BaseViewModel<DetailView> {

  Subscription subscription;
  private Book book;

  public DetailVM() {
  }

  public DetailVM(int id) {
    setBook(id);
  }

  @Override public void detach() {
    RxUtil.unSubscribeIfNeeded(subscription);
    super.detach();
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public void checkout(View v) {
    checkout();
  }

  @Override protected DetailView getEmptyView() {
    return new DetailView() {
      @Override public void finish() {

      }
    };
  }

  public Book getBook() {
    return book;
  }

  public void setBook(int id) {
    DataCenter.getInstance().getBookById(id).subscribe(book1 -> {
      book = book1;
      this.notifyChange();
    });
  }

  private void checkout() {
    subscription = DataCenter.getInstance().checkOut(book).subscribe(book1 -> {
      this.book = book1;
      this.notifyChange();
    });
  }

  public void deleteBook() {
    DataCenter.getInstance().remove(book).subscribe(book1 -> {
      view.finish();
    });
  }
}
