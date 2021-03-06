package com.grayherring.databinding.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import com.grayherring.databinding.dagger.HasComponent;

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {
  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeDependencyInjector();
  }

  @SuppressWarnings("unchecked")
  protected <C> C getComponent(Class<C> componentType) {
    return ((HasComponent<C>) getActivity()).getComponent();
  }

  /**
   * Initialize and inject your graph component, if needed.
   */
  protected abstract void initializeDependencyInjector();
}
