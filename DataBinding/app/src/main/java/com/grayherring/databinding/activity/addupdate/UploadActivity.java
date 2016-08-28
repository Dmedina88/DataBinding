package com.grayherring.databinding.activity.addupdate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.widget.Toast;
import com.grayherring.databinding.R;
import com.grayherring.databinding.activity.detail.DetailVM;
import com.grayherring.databinding.activity.detail.DetailsActivity;
import com.grayherring.databinding.activity.main.MainActivity;
import com.grayherring.databinding.base.BaseBindingActivity;
import com.grayherring.databinding.databinding.ActivityUpdateBookBinding;

/**
 * Created by David on 12/5/2015.
 */
public class UploadActivity
    extends BaseBindingActivity<ActivityUpdateBookBinding, UploadVM, AddUpdateView>
    implements AddUpdateView {

  public static void start(final Context context,final int id) {
    final Intent i = new Intent(context, UploadActivity.class);
    i.putExtra(DetailsActivity.SELECTED_ITEM,id);
    context.startActivity(i);
  }

  @Override protected void bindVM() {
    int id = getIntent().getIntExtra(DetailsActivity.SELECTED_ITEM, -1);
    if (id > -1) {
      vm = new UploadVM(getIntent().getIntExtra(DetailsActivity.SELECTED_ITEM, -1));
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