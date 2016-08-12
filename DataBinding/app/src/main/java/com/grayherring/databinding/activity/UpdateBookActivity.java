package com.grayherring.databinding.activity;

import android.os.Bundle;
import android.view.View;
import com.grayherring.databinding.model.Book;

public class UpdateBookActivity extends UploadActivity {

  // SwagActionCreator actionCenter = SwagActionCreator.getInstance();
  private Book book;
  private int position;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      //   restoreViewFromState();
    } else {
      // book = AppStore.getInstance().getState().getSelectedBook();
      //  position = AppStore.getInstance().getState().getSelectedBookIndex();
    }
    // AppStore.getInstance().register(this);
  }

  @Override protected void initializeDependencyInjector() {

  }

  //  @OnClick(R.id.submit_button)
  public void submit(View view) {
    //    if (Util.checkFieldsEmpty(titleEditText, authorEditText, publisherEditText, categoriesEditText)) {
    //      showMissingDataDialog();
    //    } else {
    //      //  progressDialog.setMessage(getString(R.string.updating));
    //      //  progressDialog.show();
    //    //  actionCenter.update(position, newBook(book),sourceId());
    //    }
  }

  @Override
  protected void onDestroy() {
    // AppStore.getInstance().unregister(this);
    super.onDestroy();
  }

  //@Override
  //public void call(final SwagChangeEvent changeEvent) {
  //  SwagAction action = changeEvent.lastAction;
  //  if (action.name.equals(SwagActionCreator.UPDATE_BOOK)) {
  //    finish();
  //    return;
  //  }
  //  super.call(changeEvent);
  //}
}
