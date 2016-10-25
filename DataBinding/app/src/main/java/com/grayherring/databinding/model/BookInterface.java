package com.grayherring.databinding.model;

/**
 * Created by davidmedina on 10/25/16 =).
 */
public interface BookInterface {
  AuthorInterface getAuthor();

  void setAuthor(RealmAuthor realmAuthor);

  void setAuthor(String author);

  String getCategories();

  void setCategories(String categories);

  String getLastCheckedOut();

  void setLastCheckedOut(String lastCheckedOut);

  String getLastCheckedOutBy();

  void setLastCheckedOutBy(String lastCheckedOutBy);

  String getPublisher();

  void setPublisher(String publisher);

  String getTitle();

  void setTitle(String title);

  Integer getId();

  void setId(Integer id);

  String getImage();

  void setImage(String image);
}
