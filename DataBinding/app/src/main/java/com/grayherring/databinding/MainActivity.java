package com.grayherring.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.grayherring.databinding.base.BaseActivity;
import com.grayherring.databinding.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(new MainVM());
        binding.mainRv.setLayoutManager(new LinearLayoutManager(this));
        binding.mainRv.setAdapter(new BookAdapter());
    }
}
