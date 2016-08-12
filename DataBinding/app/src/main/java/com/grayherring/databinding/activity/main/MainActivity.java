package com.grayherring.databinding.activity.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import com.grayherring.databinding.R;
import com.grayherring.databinding.activity.AddBookActivity;
import com.grayherring.databinding.base.BaseBindingActivity;
import com.grayherring.databinding.dagger.component.HomeComponent;
import com.grayherring.databinding.databinding.ActivityMainBinding;
import io.realm.Realm;
import javax.inject.Inject;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding, MainVM, MainView>
    implements MainView {

  BookAdapter bookAdapter;
  private SearchView searchView;
  MenuItem searchMenuItem;
   Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding.mainRv.setLayoutManager(new LinearLayoutManager(this));
    bookAdapter = new BookAdapter(vm);
    binding.mainRv.setAdapter(bookAdapter);
    // DataCenter.subscribe();

  }

  @Override protected void initializeDependencyInjector() {
    HomeComponent.Initializer.init(this).inject(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override protected int getLayoutId() {
    return 0;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    searchMenuItem = menu.findItem(R.id.search);
    searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
    //searchView.setOnQueryTextListener(this);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
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
    Intent i = new Intent(this, AddBookActivity.class);
    this.startActivity(i);
  }

  @Override public void startDetailActivity() {

  }
}
