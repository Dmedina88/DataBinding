package com.grayherring.databinding.activity.detail;

import android.view.View;
import com.grayherring.databinding.base.BaseViewModel;
import com.grayherring.databinding.data.SwagDataCenter;
import com.grayherring.databinding.model.Book;
import com.grayherring.databinding.util.RxUtil;
import rx.Subscription;

public class DetailVM extends BaseViewModel<DetailView> {

  Subscription subscription;
  private Book book;



  public DetailVM(int id, Book book) {
    if(book != null){
      setBook(book);
    }else {
      setBook(id);
    }
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

  public void showOtherBooks(View v) {
    view.listOtherBooks(book.getAuthor().getBooks().toString());
  }


  @Override protected DetailView getEmptyView() {
    return new DetailView() {
      @Override public void finish() {

      }

      @Override public void listOtherBooks(String list) {

      }
    };
  }

  public Book getBook() {
    return book;
  }

  public void setBook(int id) {
    SwagDataCenter.getInstance().getBookById(id).subscribe(book1 -> {
      book = book1;
      this.notifyChange();
    });
  }

  private void checkout() {
    subscription = SwagDataCenter.getInstance().checkOut(book).subscribe(book1 -> {
      this.book = book1;
      this.notifyChange();
    });
  }

  public void deleteBook() {
    SwagDataCenter.getInstance().remove(book).subscribe(book1 -> {
      view.finish();
    });
  }

}
