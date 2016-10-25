package com.grayherring.databinding.model;

import java.util.List;

/**
 * Created by davidmedina on 10/25/16 =).
 */
public interface AuthorInterface {
  String getName();

  void setName(String name);

  String getBirthday();

  void setBirthday(String birthday);

  List<BookInterface> getBooks();

  void setBooks(List<BookInterface> books);
}
