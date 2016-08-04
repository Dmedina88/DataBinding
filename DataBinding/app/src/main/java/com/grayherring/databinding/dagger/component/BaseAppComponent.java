package com.grayherring.databinding.dagger.component;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import com.google.gson.Gson;
import com.grayherring.databinding.base.BaseView;
import com.grayherring.databinding.base.BaseViewModel;
import com.squareup.picasso.Picasso;
import okhttp3.OkHttpClient;

public interface BaseAppComponent {
  Application application();

  Resources resources();

  SharedPreferences sharedPreferences();

  Gson gson();

  OkHttpClient client();

  Picasso picasso();


  BaseViewModel<BaseView> baseViewModel();


}
