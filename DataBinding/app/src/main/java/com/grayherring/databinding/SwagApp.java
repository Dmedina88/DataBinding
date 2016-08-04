package com.grayherring.databinding;

import android.app.Application;

import com.grayherring.databinding.data.DataCenter;
import com.grayherring.databinding.data.DataProvider;
import com.grayherring.databinding.data.provider.BookAddedDataProvider;
import com.grayherring.databinding.data.provider.NewListDataProvider;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import java.util.ArrayList;
import timber.log.Timber;

/**
 * Created by David on 4/23/2016.
 */
public class SwagApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
    Realm.setDefaultConfiguration(new RealmConfiguration.Builder(getApplicationContext()).build());

    ArrayList<DataProvider> dataProviders = new ArrayList<>();
    Timber.d("fuck");

    //dataProviders.add(new BookAddedDataProvider());
    //dataProviders.add(new NewListDataProvider());
    //DataCenter.init(dataProviders);

  }
}
