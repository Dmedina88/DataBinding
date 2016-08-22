package com.grayherring.databinding.dagger.modules;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grayherring.databinding.dagger.PerApp;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import java.io.File;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import timber.log.Timber;

import static java.util.concurrent.TimeUnit.SECONDS;

@Module
public class DataModule {
  public static final int DISK_CACHE_SIZE = (int) (50 * 1024 * 1024); // 50MB

  static OkHttpClient createOkHttpClient(
      final Application app) {
    // Install an HTTP cache in the application cache directory.
    final File cacheDir = new File(app.getCacheDir(), "http");
    final Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

    return new OkHttpClient.Builder()
        .connectTimeout(30, SECONDS)
        .readTimeout(30, SECONDS)
        .writeTimeout(30, SECONDS)
        .cache(cache)
        .build();
  }

  @Provides @PerApp Gson provideGson() {
    return new GsonBuilder().create();
  }

  @Provides @PerApp Picasso providePicasso(final Application app, final OkHttpClient client) {
    return new Picasso.Builder(app)
        .downloader(new OkHttp3Downloader(client))
        .listener((picasso, uri, e) -> Timber.e(e, "Failed to load image: %s", uri))
        .build();
  }

  @Provides @PerApp Realm provideRealm() {
    return Realm.getDefaultInstance();
  }
}
