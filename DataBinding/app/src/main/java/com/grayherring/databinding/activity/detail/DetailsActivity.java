package com.grayherring.databinding.activity.detail;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.grayherring.databinding.R;
import com.grayherring.databinding.activity.main.MainActivity;
import com.grayherring.databinding.base.BaseBindingActivity;
import com.grayherring.databinding.data.DataCenter;
import com.grayherring.databinding.databinding.ActivityDetailsBinding;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class DetailsActivity
    extends BaseBindingActivity<ActivityDetailsBinding, DetailVM, DetailView>
    implements DetailView, RealmChangeListener<Realm> {

  @Override protected void bindVM() {
    vm = new DetailVM(getIntent().getIntExtra(MainActivity.SELECTED_ITEM, 0));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // restoreViewFromState();
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

  public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();
    switch (id) {
      case R.id.action_delete:
        vm.deleteBook();
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

  @Override public void onChange(Realm element) {
    vm.setBook(getIntent().getIntExtra(MainActivity.SELECTED_ITEM, 0));
  }
}
