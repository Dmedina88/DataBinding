package com.grayherring.databinding.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by David on 12/3/2015.
 */
//@Parcel(implementations = { BookRealmProxy.class },
//        value = Parcel.Serialization.BEAN,
//        analyze = { Book.class })
@RealmClass
public class Book extends RealmObject {

  private Author author;
  private String categories;
  private String lastCheckedOut;
  private String lastCheckedOutBy;
  private String publisher;
  private String title;

  private String image;
  @PrimaryKey
  private Integer id;

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public void setAuthor(String author) {
    this.author.setName(author);
  }

  public String getCategories() {
    return categories;
  }

  public void setCategories(String categories) {
    this.categories = categories;
  }

  public String getLastCheckedOut() {
    return lastCheckedOut;
  }

  public void setLastCheckedOut(String lastCheckedOut) {
    this.lastCheckedOut = lastCheckedOut;
  }

  public String getLastCheckedOutBy() {
    return lastCheckedOutBy;
  }

  public void setLastCheckedOutBy(String lastCheckedOutBy) {
    this.lastCheckedOutBy = lastCheckedOutBy;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override public String toString() {
    return "Book{" +
        "author='" + author + '\'' +
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
