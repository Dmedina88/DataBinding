package com.grayherring.databinding.activity.detail;

import android.view.View;
import com.grayherring.databinding.base.BaseViewModel;
import com.grayherring.databinding.data.SwagDataCenter;
import com.grayherring.databinding.model.RealmBook;
import com.grayherring.databinding.model.BookInterface;
import com.grayherring.databinding.util.RxUtil;
import rx.Subscription;
import timber.log.Timber;

public class DetailVM extends BaseViewModel<DetailView> {

  private Subscription subscription;
  private BookInterface book;

  public DetailVM(final int id, final RealmBook book) {
    if (book != null) {
      Timber.d(book.toString());
      setBook(book);
    } else {
      Timber.d("##" + id);
      setBook(id);
    }
  }

  @Override public void detach() {
    RxUtil.unSubscribeIfNeeded(subscription);
    super.detach();
  }

  public void setBook(final RealmBook book) {
    this.book = book;
  }

  public void checkout(final View v) {
    checkout();
  }

  public void showOtherBooks(final View v) {
    view.listOtherBooks("i am lazy");
  }

  @Override protected DetailView getEmptyView() {
    return new DetailView() {
      @Override public void finish() {

      }

      @Override public void listOtherBooks(final String list) {

      }
    };
  }

  public BookInterface getBook() {
    return book;
  }

  public void setBook(final int id) {
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
