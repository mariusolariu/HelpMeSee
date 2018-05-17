package com.example.marius.helpmesee.location;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.marius.helpmesee.R;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public class LocationScreenViewImpl implements  LocationScreenView{
  private View rootView;
  private Context context;

  public LocationScreenViewImpl(Context context, ViewGroup container) {
    this.context = context;
    rootView = LayoutInflater.from(context).inflate(R.layout.location_screen_layout, container);
  }

  @Override
  public View getAndroidLayoutView() {
    return rootView;
  }

  @Override
  public Bundle getViewState() {
    return null;
  }
}
