package com.grayherring.databinding.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by David on 12/3/2015.
 */
//@Parcel(implementations = { BookRealmProxy.class },
//        value = Parcel.Serialization.BEAN,
//        analyze = { RealmBook.class })
@RealmClass
public class RealmBook extends RealmObject implements BookInterface {

  private RealmAuthor realmAuthor;
  private String categories;
  private String lastCheckedOut;
  private String lastCheckedOutBy;
  private String publisher;
  private String title;

  private String image;
  @PrimaryKey
  private Integer id;

  @Override public AuthorInterface getAuthor() {
    return realmAuthor;
  }

  @Override public void setAuthor(RealmAuthor realmAuthor) {
    this.realmAuthor = realmAuthor;
  }

  @Override public void setAuthor(String author) {
    this.realmAuthor.setName(author);
  }

  @Override public String getCategories() {
    return categories;
  }

  @Override public void setCategories(String categories) {
    this.categories = categories;
  }

  @Override public String getLastCheckedOut() {
    return lastCheckedOut;
  }

  @Override public void setLastCheckedOut(String lastCheckedOut) {
    this.lastCheckedOut = lastCheckedOut;
  }

  @Override public String getLastCheckedOutBy() {
    return lastCheckedOutBy;
  }

  @Override public void setLastCheckedOutBy(String lastCheckedOutBy) {
    this.lastCheckedOutBy = lastCheckedOutBy;
  }

  @Override public String getPublisher() {
    return publisher;
  }

  @Override public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  @Override public String getTitle() {
    return title;
  }

  @Override public void setTitle(String title) {
    this.title = title;
  }

  @Override public Integer getId() {
    return id;
  }

  @Override public void setId(Integer id) {
    this.id = id;
  }

  @Override public String getImage() {
    return image;
  }

  @Override public void setImage(String image) {
    this.image = image;
  }

  @Override public String toString() {
    return "RealmBook{" +
        "author='" + realmAuthor + '\'' +
        ", categories='" + categories + '\'' +
        ", lastCheckedOut='" + lastCheckedOut + '\'' +
        ", lastCheckedOutBy='" + lastCheckedOutBy + '\'' +
        ", publisher='" + publisher + '\'' +
        ", title='" + title + '\'' +
        ", image='" + image + '\'' +
        ", id=" + id +
        '}';
  }
}
