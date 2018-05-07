package com.example.marius.helpmesee.common;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public enum AppFeaturesEnum {
  DIRECTIONS("directions"), LOCATION("location"),  TEXT_RECOGNITION("text recognition"), SCENE_DESCRIPTION("scene description");

  private String featureName;

  AppFeaturesEnum(String featureName) {
    this.featureName = featureName;
  }

  @Override
  public String toString() {
    return featureName;
  }

  public static AppFeaturesEnum stringToFeature (String text){
    AppFeaturesEnum feature = null;

    for (AppFeaturesEnum f: AppFeaturesEnum.values()) {
        if (text.equals(f.toString())){
          feature = f;
        }
    }

    return feature;
  }
}
