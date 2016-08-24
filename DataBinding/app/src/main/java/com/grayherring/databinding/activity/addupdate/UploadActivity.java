package com.grayherring.databinding.activity.addupdate;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.grayherring.databinding.R;
import com.grayherring.databinding.activity.main.MainActivity;
import com.grayherring.databinding.base.BaseBindingActivity;
import com.grayherring.databinding.databinding.ActivityUpdateBookBinding;

/**
 * Created by David on 12/5/2015.
 */
public class UploadActivity
    extends BaseBindingActivity<ActivityUpdateBookBinding, UploadVM, AddUpdateView>
    implements AddUpdateView {

  @Override protected void bindVM() {
    Integer id = getIntent().getIntExtra(MainActivity.SELECTED_ITEM, -1);
    if (id != null && id > -1) {
      vm = new UploadVM(getIntent().getIntExtra(MainActivity.SELECTED_ITEM, -1));
    } else {
      vm = new UploadVM();
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected void initializeDependencyInjector() {

  }

  @Override protected int getLayoutId() {
    return R.layout.activity_update_book;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.upload_menu, menu);
    return true;
  }

  protected void showUnsavedDataDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
    builder.setTitle(getString(R.string.unsaved));
    builder.setMessage("Are you sure you want to leave with unsaved changes.");
    builder.setPositiveButton("OK", (dialog, which) -> {
      finish();
    });
    builder.setNegativeButton("Cancel", null);
    builder.show();
  }

  protected void showMissingDataDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
    builder.setTitle(getString(R.string.missing_data));
    builder.setMessage(getString(R.string.missing_data_dialog));
    builder.setNegativeButton("Ok", null);
    builder.show();
  }

  @Override public void onComplete(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }
}