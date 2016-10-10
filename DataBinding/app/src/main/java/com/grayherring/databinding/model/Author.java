package com.grayherring.databinding.model;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import java.util.List;

/**
 * Created by davidmedina on 8/27/16.
 */
//@Parcel(implementations = { AuthorRealmProxy.class },
//        value = Parcel.Serialization.BEAN,
//        analyze = { Author.class })
@RealmClass
public class Author implements RealmModel {

  private String Name;
  private String Birthday;

  private RealmList<Book> books;

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getBirthday() {
    return Birthday;
  }

  public void setBirthday(String birthday) {
    Birthday = birthday;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(RealmList<Book> books) {
    this.books = books;
  }

  public void setBooks(List<Book> books) {
    RealmList<Book> realmList = new RealmList<>();
    realmList.addAll(books);
    this.books = realmList;
  }
}
