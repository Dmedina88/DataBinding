package com.grayherring.databinding;

import android.app.Application;
import android.content.Context;
import com.grayherring.databinding.dagger.component.AppComponent;
import com.grayherring.databinding.data.DefaultDataCenter;
import com.grayherring.databinding.data.SwagDataCenter;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by David on 4/23/2016 =).
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
    Realm.init(getApplicationContext());
    Realm.setDefaultConfiguration(new RealmConfiguration.Builder().build());
    component = initComponent();
    component.inject(this);

    SwagDataCenter.init(new DefaultDataCenter());
    //ArrayList<DataProvider> dataProviders = new ArrayList<>();
    //
    //dataProviders.add(new BookAddedDataProvider());
    //dataProviders.add(new NewListDataProvider());

    // SwagDataCenter.init();
  }

  private AppComponent initComponent() {
    return AppComponent.Initializer.init(this);
  }
}
