package com.grayherring.databinding.activity.main;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import com.grayherring.databinding.R;
import com.grayherring.databinding.activity.addupdate.UploadActivity;
import com.grayherring.databinding.activity.detail.DetailsActivity;
import com.grayherring.databinding.base.BaseBindingActivity;
import com.grayherring.databinding.data.SwagDataCenter;
import com.grayherring.databinding.databinding.ActivityMainBinding;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding, MainVM, MainView>
    implements MainView, SearchView.OnQueryTextListener, RealmChangeListener<Realm> {

  public static final String SELECTED_ITEM = "SELECTED_ITEM";

  BookAdapter bookAdapter;
  MenuItem searchMenuItem;
  Realm realm;

  @Override protected void bindVM() {
    vm = new MainVM();
  }

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding.mainRv.setLayoutManager(new LinearLayoutManager(this));
    bookAdapter = new BookAdapter(vm);
    binding.mainRv.setAdapter(bookAdapter);
    SwagDataCenter.getInstance().addRealmChangeListener(this);
  }

  @Override protected void initializeDependencyInjector() {
    // HomeComponent.Initializer.init(this).inject(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    SwagDataCenter.getInstance().removeRealmChangeListener(this);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    searchMenuItem = menu.findItem(R.id.search);
    final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
    searchView.setOnQueryTextListener(this);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    final int id = item.getItemId();
    switch (id) {
      case R.id.action_add:
        startAddActivity();
        break;
      case R.id.action_seed:
        vm.seed();
        break;
      case R.id.action_delete_all:
        vm.delete();
        break;
    }
    return true;
  }

  @Override public void startAddActivity() {
    UploadActivity.start(this, -1);
  }

  @Override public void startDetailActivity(final int id) {
    DetailsActivity.start(this, id);
  }

  @Override public boolean onQueryTextSubmit(final String query) {
    return true;
  }

  @Override public boolean onQueryTextChange(final String newText) {
    vm.search(newText);
    return false;
  }

  @Override public void onChange(final Realm element) {
    vm.getAllData(null);
  }
}
