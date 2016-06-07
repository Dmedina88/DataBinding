package com.grayherring.databinding.main;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.grayherring.databinding.base.DataCenter;
import com.grayherring.databinding.model.Book;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by David on 6/4/2016.
 */

public class MainVM  {
  ObservableArrayList<Book> books = new ObservableArrayList<>();

  public MainVM() {

    DataCenter.getInstance().seed().subscribe(books1 -> {
      books.addAll(books1);
    });
  }

  public List<Book> getBooks() {
    return books;
  }
}
