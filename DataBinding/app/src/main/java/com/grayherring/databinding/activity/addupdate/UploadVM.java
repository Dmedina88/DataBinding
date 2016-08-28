package com.grayherring.databinding.activity.addupdate;

import android.view.View;
import com.grayherring.databinding.base.BaseViewModel;
import com.grayherring.databinding.data.DataCenter;
import com.grayherring.databinding.data.SwagDataCenter;
import com.grayherring.databinding.model.Author;
import com.grayherring.databinding.model.Book;
import timber.log.Timber;

/**
 * Created by davidmedina on 8/21/16.
 */

public class UploadVM extends BaseViewModel<AddUpdateView> {

  private final DataCenter dataCenter = SwagDataCenter.getInstance();
  private Book book;

  public UploadVM() {
    book = new Book();
    book.setAuthor(new Author());
    this.notifyChange();
  }

  public UploadVM(int id) {
    setBook(id);
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public void addBook() {
    dataCenter.add(book).subscribe(book1 -> {
      book = book1;
      this.notifyChange();
      view.onComplete("book added");
    });
  }

  public void update() {
    dataCenter.update(book).subscribe(book1 -> {
      book = book1;
      this.notifyChange();
      view.onComplete("book update");
    });
  }

  public Book getBook() {
    return book;
  }

  public void setBook(int id) {
    SwagDataCenter.getInstance().getBookById(id).subscribe(book1 -> {
      Timber.d(book1.toString());
      book = book1;
      this.notifyChange();
    });
  }

  public void submitClicked(View v) {
    Timber.d("## submitClicked");
    if (book.getId() != null && book.getId() > -1) {
      Timber.d("## submitClicked add");
      this.update();
    } else {
      Timber.d("## submitClicked update");
      this.addBook();
    }
  }

  @Override protected AddUpdateView getEmptyView() {
    return message -> {

    };
  }
}
