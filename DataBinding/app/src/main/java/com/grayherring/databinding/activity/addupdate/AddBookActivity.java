package com.grayherring.databinding.activity.addupdate;

import android.os.Bundle;
import android.view.View;

/**
 * Created by David on 12/5/2015.
 */
public class AddBookActivity extends UploadActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected void initializeDependencyInjector() {

  }

  public void submit(View view) {

    //if (Util.checkFieldsEmpty(titleEditText, authorEditText, publisherEditText,
    //    categoriesEditText)) {
    //  showMissingDataDialog();
    //} else {
    //  progressDialog.setMessage(getString(R.string.adding));
    //  progressDialog.show();
    //  swagActionCreator.add(newBook(null),sourceId());
    //}
  }

  @Override protected void onDestroy() {
    //  AppStore.getInstance().unregister(this);
    super.onDestroy();
  }

  //@Override
  //public void call(SwagChangeEvent swagChangeEvent) {
  //  SwagAction action = swagChangeEvent.lastAction;
  //  if (action.name.equals(SwagActionCreator.ADD_BOOK)) {
  //    progressDialog.hide();
  //    finish();
  //  }
  //}
  //
  //@Override public void restoreViewFromState() {
  //
  //}
}
