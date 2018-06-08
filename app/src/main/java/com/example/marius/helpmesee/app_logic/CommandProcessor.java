package com.example.marius.helpmesee.app_logic;

import android.content.Context;
import android.content.Intent;
import com.example.marius.helpmesee.directions.presenter.DirectionsScreenPresenter;
import com.example.marius.helpmesee.location.LocationScreenPresenter;
import com.example.marius.helpmesee.scene_description.SceneDescPresenter;
import com.example.marius.helpmesee.text_recognition.TextRecPresenter;
import com.example.marius.helpmesee.util.HmsActivity;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public class CommandProcessor {

  private static final CommandProcessor ourInstance = new CommandProcessor();

  private static final Class<?> directionPresClass = DirectionsScreenPresenter.class;
  private static final Class<?> locationPresClass = LocationScreenPresenter.class;
  private static final Class<?> textRecClass = TextRecPresenter.class;
  private static final Class<?> sceneDescClass = SceneDescPresenter.class;


  public static CommandProcessor getInstance() {

    return ourInstance;
  }

  /**
   * @param detectedText - detected text
   */
  public void processCommand(String detectedText,  HmsActivity hmsActivity) {
    AppFeaturesEnum feature = AppFeaturesEnum.stringToFeature(detectedText);

    //if it's not a feature then it might be another command like "Take Picture"

    if (feature != null) {
      changeScreen(feature, hmsActivity.getBaseContext());
    }//process other types of commands
    else {
      hmsActivity.execute(detectedText);
    }

      //Log.i(Constants.HMS_INFO, "Cannot process: " + detectedText);
  }

  private void changeScreen(AppFeaturesEnum feature,  Context currentContext) {
   // Context currentContext = AppState.getInstance().getCurrentContext();

    switch (feature) {
      case DIRECTIONS:
        currentContext.startActivity(new Intent(currentContext, directionPresClass));
        break;

      case LOCATION:
        currentContext.startActivity(new Intent(currentContext, locationPresClass));
        break;

      case TEXT_RECOGNITION:
        currentContext.startActivity(new Intent(currentContext, textRecClass));
        break;

      case SCENE_DESCRIPTION:
        currentContext.startActivity(new Intent(currentContext, sceneDescClass));
        break;
    }
  }


}
