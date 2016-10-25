package com.grayherring.databinding.model;

import io.realm.Realm;

/**
 * Created by davidmedina on 10/25/16 =).
 */

public class Book implements  BookInterface{

  private RealmBook realmBook;

  public Book(final RealmBook realmBook) {
    this.realmBook = realmBook;
  }

  @Override public AuthorInterface getAuthor() {
    return realmBook.getAuthor();
  }

  @Override public void setAuthor(final RealmAuthor realmAuthor) {
   Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(realm1 -> {
      realmBook.setAuthor(realmAuthor);
          realm1.close();
    });

  }

  @Override public void setAuthor(final String author) {
    realmBook.setAuthor(author);
  }

  @Override public String getCategories() {
    return realmBook.getCategories();
  }

  @Override public void setCategories(final String categories) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(realm1 -> {
      realmBook.setCategories(categories);
      realm1.close();
    });
  }


  @Override public String getLastCheckedOut() {
    return realmBook.getLastCheckedOut();
  }

  @Override public void setLastCheckedOut(final String lastCheckedOut) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(realm1 -> {
      realmBook.setLastCheckedOut(lastCheckedOut);
      realm1.close();
    });
  }

  @Override public String getLastCheckedOutBy() {
    return null;
  }

  @Override public void setLastCheckedOutBy(final String lastCheckedOutBy) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(realm1 -> {
      realmBook.setLastCheckedOutBy(lastCheckedOutBy);
      realm1.close();
    });
  }

  @Override public String getPublisher() {
    return null;
  }

  @Override public void setPublisher(final String publisher) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(realm1 -> {
      realmBook.setPublisher(publisher);
      realm1.close();
    });
  }

  @Override public String getTitle() {
    return null;
  }

  @Override public void setTitle(final String title) {

  }

  @Override public Integer getId() {
    return null;
  }

  @Override public void setId(final Integer id) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(realm1 -> {
      realmBook.setId(id);
      realm1.close();
    });
  }

  @Override public String getImage() {
    return null;
  }

  @Override public void setImage(final String image) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(realm1 -> {
      realmBook.setImage(image);
      realm1.close();
    });

  }

  public void setRealmBook(final RealmBook realmBook) {
    this.realmBook = realmBook;
  }
}
