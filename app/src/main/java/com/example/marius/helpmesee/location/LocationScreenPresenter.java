package com.example.marius.helpmesee.location;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public class LocationScreenPresenter extends LocationScreenListener {
  private LocationScreenView rootView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //null is passed beacause the layout is the parent of all views, i.e. container=none
    rootView = new LocationScreenViewImpl(this, null);
    setContentView(rootView.getAndroidLayoutView());

  }


  @Override
  public void execute(String detectedText) {

  }
}
