package com.grayherring.databinding.data.provider;

import com.grayherring.databinding.data.DataObserver;
import com.grayherring.databinding.model.Book;
import java.util.List;

public interface NewListObserver extends DataObserver {

  void getNewList(List<Book> books);
}
