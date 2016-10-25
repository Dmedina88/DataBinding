package com.grayherring.databinding.model;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidmedina on 8/27/16.
 */
//@Parcel(implementations = { AuthorRealmProxy.class },
//        value = Parcel.Serialization.BEAN,
//        analyze = { RealmAuthor.class })
@RealmClass
public class RealmAuthor implements RealmModel, AuthorInterface {

  private String Name;
  private String Birthday;

  private List<BookInterface> books;

  @Override public String getName() {
    return Name;
  }

  @Override public void setName(String name) {
    Name = name;
  }

  @Override public String getBirthday() {
    return Birthday;
  }

  @Override public void setBirthday(String birthday) {
    Birthday = birthday;
  }

  @Override public List<BookInterface> getBooks() {
    return books;
  }

  @Override public void setBooks(List<BookInterface> books) {
    List<BookInterface> realmList = new ArrayList<>();
    realmList.addAll(books);
    this.books = realmList;
  }
}
