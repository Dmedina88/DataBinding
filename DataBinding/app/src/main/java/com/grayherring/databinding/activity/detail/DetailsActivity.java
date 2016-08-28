package com.grayherring.databinding.activity.detail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.grayherring.databinding.R;
import com.grayherring.databinding.activity.addupdate.UploadActivity;
import com.grayherring.databinding.base.BaseBindingActivity;
import com.grayherring.databinding.data.SwagDataCenter;
import com.grayherring.databinding.databinding.ActivityDetailsBinding;
import com.grayherring.databinding.model.Book;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import org.parceler.Parcels;

public class DetailsActivity
    extends BaseBindingActivity<ActivityDetailsBinding, DetailVM, DetailView>
    implements DetailView, RealmChangeListener<Realm> {

  public static final String SELECTED_ITEM = "SELECTED_ITEM";
  public static final String SELECTED_ITEM_ID = "SELECTED_ITEM_ID";

  public static void start(Context context, int id) {
    final Intent i = new Intent(context, DetailsActivity.class);
    i.putExtra(SELECTED_ITEM_ID, id);
    context.startActivity(i);
  }

  public static void start(Context context, Book book) {
    final Intent i = new Intent(context, DetailsActivity.class);
    i.putExtra(SELECTED_ITEM, Parcels.wrap(book));
    context.startActivity(i);
  }

  @Override protected void bindVM() {
    Intent i = getIntent();
    vm = new DetailVM(i.getIntExtra(SELECTED_ITEM, 0), i.getParcelableExtra(SELECTED_ITEM));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // restoreViewFromState();
    SwagDataCenter.getInstance().addRealmChangeListener(this);
  }

  @Override protected void initializeDependencyInjector() {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    SwagDataCenter.getInstance().addRealmChangeListener(this);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_details;
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.action_delete:
        vm.deleteBook();
        break;
      case R.id.action_update:
        UploadActivity.start(this, vm.getBook().getId());
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_detail, menu);
    return true;
  }

  @Override public void onChange(Realm element) {
    vm.setBook(getIntent().getIntExtra(SELECTED_ITEM, 0));
  }

  @Override public void listOtherBooks(String list) {
    new AlertDialog
        .Builder(this)
        .setMessage(list)
        .create()
        .show();
  }
}
