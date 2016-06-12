package com.grayherring.databinding.activity.main;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ObservableList.OnListChangedCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grayherring.databinding.R;
import com.grayherring.databinding.databinding.ItemBookBinding;

/**
 * Created by David on 6/4/2016.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookVH> {

  MainVM mainVM;


  public BookAdapter(MainVM mainVM) {
    this.mainVM = mainVM;
    mainVM.books.addOnListChangedCallback(new OnListChangedCallback() {
      @Override
      public void onChanged(ObservableList observableList) {
        BookAdapter.this.notifyDataSetChanged();
      }

      @Override
      public void onItemRangeChanged(ObservableList observableList, int positionStart, int itemCount) {
        BookAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
      }

      @Override
      public void onItemRangeInserted(ObservableList observableList, int positionStart, int itemCount) {
        BookAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
      }

      @Override
      public void onItemRangeMoved(ObservableList observableList, int i, int positionStart, int itemCount) {
        BookAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
      }

      @Override
      public void onItemRangeRemoved(ObservableList observableList, int positionStart, int itemCount) {
        BookAdapter.this.notifyDataSetChanged();

      }
    });
  }

  @Override
  public BookVH onCreateViewHolder(ViewGroup parent, int viewType) {
    ItemBookBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_book, parent, false);

    return new BookVH(binding.getRoot());
  }

  @Override
  public void onBindViewHolder(BookVH holder, int position) {
    holder.binding.setBook(mainVM.books.get(position));
  }

  @Override
  public int getItemCount() {
    return mainVM.books.size();
  }



  public static class BookVH extends RecyclerView.ViewHolder {

    ItemBookBinding binding;

    public BookVH(View itemView) {
      super(itemView);
      binding = DataBindingUtil.bind(itemView);
    }
  }
}
