package com.example.marius.helpmesee.mainscreen.presenters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.marius.helpmesee.common.AppFeaturesEnum;
import com.example.marius.helpmesee.mainscreen.views.MainMenuScreenView;
import com.example.marius.helpmesee.mainscreen.views.MainMenuScreenView.MainMenuScreenListener;
import com.example.marius.helpmesee.mainscreen.views.MainMenuScreenViewImpl;
import java.util.ArrayList;
import java.util.List;

public class MainScreenPresenter extends AppCompatActivity implements
    MainMenuScreenListener {

  // //the model goes here if any
  private MainMenuScreenView rootView; // ui element of this presenter
  private List<String> appFeaturesList;
  private static final String MAIN_SCREEN_TAG = "MainScreen";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

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
      case LOCATION:
        Log.i(MAIN_SCREEN_TAG, "Launch Location screen");
        break;

      case DIRECTIONS:
        Log.i(MAIN_SCREEN_TAG, "Launch DIRECTIONS screen");
        break;

      case SCENE_DESCRIPTION:
        Log.i(MAIN_SCREEN_TAG, "Launch SCENE_DESCRIPTION screen");
        break;

      case TEXT_RECOGNITION:
        Log.i(MAIN_SCREEN_TAG, "Launch TEXT_RECOGNITION screen");
        break;
      
      default:
        Log.e(MAIN_SCREEN_TAG, "Feature doesn't exist!");
    }
  }
}
