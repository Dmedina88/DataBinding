package com.grayherring.databinding.util.datbinding;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.grayherring.databinding.R;
import com.squareup.picasso.Picasso;
import timber.log.Timber;

/**
 * Created by David on 6/6/2016.
 */
public class Bindings {

  private Bindings() {
    throw new AssertionError("No instances.");
  }

  @BindingAdapter("imageUrl")
  public static void loadImage(ImageView view, String url) {
    if (!TextUtils.isEmpty(url)) {
      Timber.d("loadImage: "+ url);
      Picasso.with(view.getContext())
          .load(url)
          .fit()
          .error(android.support.v7.appcompat.R.drawable.abc_ic_star_half_black_36dp)
          .into(view);
    }
  }
}
