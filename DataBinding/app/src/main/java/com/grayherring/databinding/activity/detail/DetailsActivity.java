package com.grayherring.databinding.activity.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.grayherring.databinding.R;
import com.grayherring.databinding.activity.UpdateBookActivity;
import com.grayherring.databinding.base.BaseActivity;
import com.grayherring.databinding.model.Book;

public class DetailsActivity extends BaseActivity {

  private Intent shareIntent;
  private int position;
  private Book book;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
   // restoreViewFromState();
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override protected void initializeDependencyInjector() {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  protected void onResume() {
    shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.setType("text/plain");
    shareIntent.putExtra(Intent.EXTRA_TEXT, book.getTitle());
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
      case R.id.action_delete:
        deleteBook();
        break;
      case R.id.action_update:
        Intent i = new Intent(this, UpdateBookActivity.class);
        //  i.putExtra(Constants.INDEX, position);
        // i.putExtra(Constants.BOOK_KEY, book);
      //  actionCenter.startActivity(i, this.sourceId());
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


}
