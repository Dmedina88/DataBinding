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

  public BookAdapter(final MainVM mainVM) {
    this.mainVM = mainVM;
    mainVM.books.addOnListChangedCallback(new OnListChangedCallback() {
      @Override
      public void onChanged(ObservableList observableList) {
        BookAdapter.this.notifyDataSetChanged();
      }

      @Override
      public void onItemRangeChanged(final ObservableList observableList, final int positionStart,
          final int itemCount) {
        BookAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
      }

      @Override
      public void onItemRangeInserted(final ObservableList observableList, final int positionStart,
          final int itemCount) {
        BookAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
      }

      @Override
      public void onItemRangeMoved(final ObservableList observableList, final int i, final int positionStart,
          final int itemCount) {
        BookAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
      }

      @Override
      public void onItemRangeRemoved(final ObservableList observableList, final int positionStart,
          final int itemCount) {
        BookAdapter.this.notifyDataSetChanged();
      }
    });
  }

  @Override
  public BookVH onCreateViewHolder(final ViewGroup parent, final int viewType) {
    final ItemBookBinding binding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_book,
            parent, false
        );

    return new BookVH(binding.getRoot());
  }

  @Override
  public void onBindViewHolder(final BookVH holder, final int position) {
    holder.binding.setBook(mainVM.books.get(position));
    holder.binding.getRoot().setOnClickListener(
        v -> mainVM.startDetailView(mainVM.books.get(position).getId()));
  }

  @Override
  public int getItemCount() {
    return mainVM.books.size();
  }

  public static class BookVH extends RecyclerView.ViewHolder {

    ItemBookBinding binding;

    public BookVH(final View itemView) {
      super(itemView);
      binding = DataBindingUtil.bind(itemView);
    }
  }
}
