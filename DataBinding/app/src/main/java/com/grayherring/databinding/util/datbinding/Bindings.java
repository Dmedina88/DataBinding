package com.grayherring.databinding.util.datbinding;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
      Picasso.with(view.getContext())
          .load(url)
          .fit()
          .centerCrop()
          .into(view);
    }
  }
}
