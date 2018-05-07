package com.example.marius.helpmesee.common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.marius.helpmesee.directions.DirectionsScreenPresenter;
import com.example.marius.helpmesee.location.LocationScreenPresenter;
import com.example.marius.helpmesee.scene_description.SceneDescPresenter;
import com.example.marius.helpmesee.text_recognition.TextRecPresenter;

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
  public void processCommand(String detectedText) {
    AppFeaturesEnum feature = AppFeaturesEnum.stringToFeature(detectedText);

    //if it's not a feature then it might be another command like "Take Picture"

    if (feature != null) {
      changeScreen(feature);
    }//process other types of commands
    else {
      Log.i(Constants.HMS_INFO, "Cannot process: " + detectedText);
    }

  }

  private void changeScreen(AppFeaturesEnum feature) {
    Context currentContext = AppState.getInstance().getCurrentContext();

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
