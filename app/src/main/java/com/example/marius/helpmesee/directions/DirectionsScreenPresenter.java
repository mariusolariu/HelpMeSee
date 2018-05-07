package com.example.marius.helpmesee.directions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.marius.helpmesee.common.HmsActivity;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public class DirectionsScreenPresenter extends HmsActivity {
  private DirectionsScreenView rootView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    rootView = new DirectionsScreenViewImpl(this, null);

  }

}
