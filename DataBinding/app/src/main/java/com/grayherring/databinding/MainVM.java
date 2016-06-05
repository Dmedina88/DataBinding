package com.grayherring.databinding;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.grayherring.databinding.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 6/4/2016.
 */

public class MainVM  {
  ObservableArrayList<Book> books = new ObservableArrayList<>();

  public MainVM() {

  }

  public List<Book> getBooks() {
    return books;
  }
}
