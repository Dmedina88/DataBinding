package com.grayherring.databinding.activity.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.grayherring.databinding.R;
import com.grayherring.databinding.activity.addupdate.UploadActivity;
import com.grayherring.databinding.activity.main.MainActivity;
import com.grayherring.databinding.base.BaseBindingActivity;
import com.grayherring.databinding.data.DataCenter;
import com.grayherring.databinding.databinding.ActivityDetailsBinding;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class DetailsActivity
    extends BaseBindingActivity<ActivityDetailsBinding, DetailVM, DetailView>
    implements DetailView, RealmChangeListener<Realm> {

  private Intent shareIntent;

  @Override protected void bindVM() {
    vm = new DetailVM(getIntent().getIntExtra(MainActivity.SELECTED_ITEM, 0));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // restoreViewFromState();
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    DataCenter.getInstance().addRealmChangeListener(this);
  }

  @Override protected void initializeDependencyInjector() {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    DataCenter.getInstance().addRealmChangeListener(this);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_details;
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  public void checkOut(View view) {
    // actionCenter.checkOut(position, book, sourceId());
  }

  @SuppressLint("SetTextI18n")
  private void updateTextViews() {
    Resources resources = this.getResources();
    //if (TextUtils.isEmpty(book.getTitle())) {
    //  bookTextView.setVisibility(View.GONE);
    //} else {
    //  bookTextView.setVisibility(View.VISIBLE);
    //  bookTextView.setText(book.getTitle());
    //}
    //
    //if (TextUtils.isEmpty(book.getAuthor())) {
    //  authorsTextView.setVisibility(View.GONE);
    //} else {
    //  authorsTextView.setVisibility(View.VISIBLE);
    //  authorsTextView.setText(book.getAuthor());
    //}
    //if (TextUtils.isEmpty(book.getCategories())) {
    //  categoriesTextView.setVisibility(View.GONE);
    //} else {
    //  categoriesTextView.setVisibility(View.VISIBLE);
    //  categoriesTextView.setText(
    //      resources.getString(R.string.categories) + " " + book.getCategories());
    //}
    //if (TextUtils.isEmpty(book.getPublisher())) {
    //  publisherTextView.setVisibility(View.GONE);
    //} else {
    //  publisherTextView.setVisibility(View.VISIBLE);
    //  publisherTextView.setText(
    //      resources.getString(R.string.publisher) + " " + book.getPublisher());
    //}
    //
    //if (TextUtils.isEmpty(book.getLastCheckedOutBy()) || TextUtils.isEmpty(
    //    book.getLastCheckedOut())) {
    //  checkOutInfoTextView.setVisibility(View.GONE);
    //  lastOutTextView.setVisibility(View.GONE);
    //} else {
    //  checkOutInfoTextView.setVisibility(View.VISIBLE);
    //  lastOutTextView.setVisibility(View.VISIBLE);
    //  checkOutInfoTextView.setText(
    //      book.getLastCheckedOutBy() + " " + "@" + " " + book.getLastCheckedOut());
    //}
  }

  public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();
    switch (id) {
      case R.id.home:
        finish();
        break;
      case R.id.action_delete:
        deleteBook();
        break;
      case R.id.action_update:
        Intent i = new Intent(this, UploadActivity.class);
        i.putExtra(MainActivity.SELECTED_ITEM, vm.getBook().getId());
        this.startActivity(i);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_detail, menu);
    MenuItem item = menu.findItem(R.id.action_share);
    //  shareActionProvider = new ShareActionProvider(this);
    //  MenuItemCompat.setActionProvider(item, shareActionProvider);
    //  shareActionProvider.setShareIntent(shareIntent);
    return true;
  }

  private void deleteBook() {
    //progressDialog.setMessage(getString(R.string.deleting));
    //progressDialog.show();
    //actionCenter.remove(position, book, sourceId());
  }

  @Override public void onChange(Realm element) {
    vm.setBook(getIntent().getIntExtra(MainActivity.SELECTED_ITEM, 0));
  }
}
