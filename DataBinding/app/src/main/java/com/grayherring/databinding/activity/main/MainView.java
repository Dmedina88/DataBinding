package com.grayherring.databinding.activity.main;

import com.grayherring.databinding.base.BaseView;

public interface MainView extends BaseView {

  void startAddActivity();

  void startDetailActivity(int position);
}
