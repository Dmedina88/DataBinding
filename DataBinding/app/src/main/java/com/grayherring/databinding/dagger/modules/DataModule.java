package com.grayherring.databinding.dagger.modules;

import android.app.Application;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grayherring.databinding.base.BaseView;
import com.grayherring.databinding.base.BaseViewModel;
import com.grayherring.databinding.dagger.PerApp;
import com.jakewharton.picasso.OkHttp3Downloader;

import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import java.io.File;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;
import static java.util.concurrent.TimeUnit.SECONDS;

@Module
public class DataModule {
  public static final int DISK_CACHE_SIZE = (int) (50 * 1024 * 1024); // 50MB
  private static final String PREFERENCES_NAME = "ppCare";



  static OkHttpClient createOkHttpClient(Application app) {
    // Install an HTTP cache in the application cache directory.
    File cacheDir = new File(app.getCacheDir(), "http");
    Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

    return new OkHttpClient.Builder()
        .connectTimeout(10, SECONDS)
        .readTimeout(10, SECONDS)
        .writeTimeout(10, SECONDS)
        .cache(cache)
        .build();
  }

  @Provides @PerApp Realm provideRealm() {
    return Realm.getDefaultInstance();
  }

  @Provides @PerApp SharedPreferences provideSharedPreferences(Application app) {
    return app.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
  }

  @Provides @PerApp Gson provideGson() {
    return new GsonBuilder().create();
  }


  @Provides @PerApp Picasso providePicasso(Application app, OkHttpClient client) {
    return new Picasso.Builder(app)
        .downloader(new OkHttp3Downloader(client))
        .listener((picasso, uri, e) -> Timber.e(e, "Failed to load image: %s", uri))
        .build();
  }

  @Provides @PerApp BaseViewModel<BaseView> provideEmptyViewModel() {
    return new BaseViewModel<BaseView>(BaseView.class) {
      @Override protected BaseView getEmptyView() {
        return null;
      }
    };
  }
}