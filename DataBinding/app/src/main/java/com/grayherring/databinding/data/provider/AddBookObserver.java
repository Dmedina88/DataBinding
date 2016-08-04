package com.grayherring.databinding.data.provider;

import com.grayherring.databinding.data.DataObserver;
import com.grayherring.databinding.model.Book;

public interface AddBookObserver extends DataObserver {
  void bookAdded(Book book);
}
