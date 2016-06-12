package com.grayherring.databinding.base;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by David on 6/5/2016.
 */
public class BaseActivity<T extends BaseViewModel>  extends AppCompatActivity {

 protected T viewModel;


  @Override protected void onDestroy() {
    viewModel.detach();
    super.onDestroy();
  }
}
