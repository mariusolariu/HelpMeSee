package com.example.marius.helpmesee.mainscreen;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import com.example.marius.helpmesee.common.AppFeaturesEnum;
import com.example.marius.helpmesee.common.AppState;
import com.example.marius.helpmesee.directions.DirectionsScreenPresenter;
import java.util.ArrayList;
import java.util.List;

public class MainScreenPresenter extends MainMenuScreenListener {

  // //the model goes here if any
  private MainMenuScreenView rootView; // ui element of this presenter
  private List<String> appFeaturesList;
  private static final String MAIN_SCREEN_TAG = "MainScreen";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    AppState.getInstance().setCurrentContext(this);
    detectPhoneSize();

    rootView = new MainMenuScreenViewImpl(this, null);
    setContentView(rootView.getRootView());

    addAppFeatures();

    rootView.setListener(this);
    rootView.displayAppFeatures(appFeaturesList);

  }


  private void addAppFeatures() {
    appFeaturesList = new ArrayList<>();

    for (AppFeaturesEnum feature : AppFeaturesEnum.values()
        ) {
      appFeaturesList.add(feature.toString());
    }
  }


  @Override
  public void onFeatureSelected(long id) {
    final AppFeaturesEnum selectedFeature = AppFeaturesEnum.valueOf(appFeaturesList.get((int) id));

    //TODO : launch the selected feature
    switch (selectedFeature) {
      case DIRECTIONS:
        Log.i(MAIN_SCREEN_TAG, "Launch DIRECTIONS screen");

        Intent i = new Intent(MainScreenPresenter.this, DirectionsScreenPresenter.class);
        startActivity(i);
        this.finish();
        break;

      case LOCATION:
        Log.i(MAIN_SCREEN_TAG, "Launch Location screen");
        break;

      case SCENE_DESCRIPTION:
        Log.i(MAIN_SCREEN_TAG, "Launch SCENE_DESCRIPTION screen");
        break;

      case TEXT_RECOGNITION:
        Log.i(MAIN_SCREEN_TAG, "Launch sEXT_RECOGNITION screen");
        break;

      default:
        Log.e(MAIN_SCREEN_TAG, "Feature doesn't exist!");
    }
  }

  private void detectPhoneSize() {
    Display display = getWindowManager().getDefaultDisplay();
    Point size = new Point();
    DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

    display.getSize(size);

    int width_pixel = size.x;
    int height_pixel = size.y;

    AppState.getInstance().setPhone_height_dp((int) (height_pixel / displayMetrics.density));
    AppState.getInstance().setPhone_width_dp(((int) (width_pixel / displayMetrics.density)));


  }
}
