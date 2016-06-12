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
import com.grayherring.databinding.base.BaseActivity;
import com.grayherring.databinding.base.BaseView;
import com.grayherring.databinding.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity<MainVM>  implements MainView{

  BookAdapter bookAdapter;
  private SearchView searchView;
  MenuItem searchMenuItem;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    viewModel = new MainVM(this);
    binding.setVm(viewModel);
    binding.mainRv.setLayoutManager(new LinearLayoutManager(this));
    bookAdapter = new BookAdapter(viewModel);
    binding.mainRv.setAdapter(bookAdapter);
  }

  @Override
  protected void onDestroy() {
    //DataCenter.unregister(this);
    super.onDestroy();
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
        viewModel.seed();
        break;
      case R.id.action_delete_all:
        viewModel.delete();
        break;
    }
    return true;
  }

  @Override public void startAddActivity() {
    Intent i = new Intent(this,AddBookActivity.class);
    this.startActivity(i);
  }

  @Override public void startDetailActivity() {

  }
}
