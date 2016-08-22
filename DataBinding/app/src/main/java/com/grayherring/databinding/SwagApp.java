package com.grayherring.databinding;

import android.app.Application;
import android.content.Context;
import com.grayherring.databinding.dagger.component.AppComponent;
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

  private AppComponent component;

  public static SwagApp get(final Context context) {
    return ((SwagApp) context.getApplicationContext());
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
    Realm.setDefaultConfiguration(new RealmConfiguration.Builder(getApplicationContext()).build());
    component = initComponent();
    component.inject(this);

    ArrayList<DataProvider> dataProviders = new ArrayList<>();

    dataProviders.add(new BookAddedDataProvider());
    dataProviders.add(new NewListDataProvider());

    DataCenter.init(dataProviders);
  }

  private AppComponent initComponent() {
    return AppComponent.Initializer.init(this);
  }
}
