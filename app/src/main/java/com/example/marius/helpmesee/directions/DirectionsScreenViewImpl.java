package com.example.marius.helpmesee.directions;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.marius.helpmesee.R;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public class DirectionsScreenViewImpl implements DirectionsScreenView {
  private View rootView;
  private Context context;

  public DirectionsScreenViewImpl(Context context, ViewGroup container){
    this.context = context;
    rootView = LayoutInflater.from(context).inflate(R.layout.directions_screen_layout, container);
  }

  @Override
  public View getRootView() {
    return rootView;
  }

  @Override
  public Bundle getViewState() {
    return null;
  }
}
