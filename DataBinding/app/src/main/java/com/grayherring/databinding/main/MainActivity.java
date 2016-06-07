package com.grayherring.databinding.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.grayherring.databinding.R;
import com.grayherring.databinding.base.BaseActivity;
import com.grayherring.databinding.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity {

  BookAdapter bookAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    MainVM mainVM = new MainVM();
    binding.setVm(mainVM);
    binding.mainRv.setLayoutManager(new LinearLayoutManager(this));
    bookAdapter = new BookAdapter(mainVM);
    binding.mainRv.setAdapter(bookAdapter);
  }
}
