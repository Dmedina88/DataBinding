package com.grayherring.databinding.model;

import java.util.List;

/**
 * Created by davidmedina on 10/25/16 =).
 */

public class Author implements AuthorInterface {

  private RealmAuthor realmAuthor;

  @Override public String getName() {
    return null;
  }

  @Override public void setName(final String name) {

  }

  @Override public String getBirthday() {
    return null;
  }

  @Override public void setBirthday(final String birthday) {

  }

  @Override public List<BookInterface> getBooks() {
    return null;
  }

  @Override public void setBooks(final List<BookInterface> books) {

  }

  public void setRealmAuthor(final RealmAuthor realmAuthor) {
    this.realmAuthor = realmAuthor;
  }
}
