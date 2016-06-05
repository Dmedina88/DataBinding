package com.grayherring.databinding;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by David on 6/4/2016.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookVH> {


  @Override
  public BookVH onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(BookVH holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  public static class BookVH extends RecyclerView.ViewHolder{


    public BookVH(View itemView) {
      super(itemView);
    }
  }
}
