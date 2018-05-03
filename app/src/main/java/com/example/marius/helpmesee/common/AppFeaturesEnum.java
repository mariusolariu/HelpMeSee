package com.example.marius.helpmesee.common;

/**
 * Created by Marius
 */

public enum AppFeaturesEnum {
  LOCATION("LOCATION"), DIRECTIONS("DIRECTIONS"), TEXT_RECOGNITION("TEXT_RECOGNITION"), SCENE_DESCRIPTION("SCENE_DESCRIPTION");

  private String featureName;

  AppFeaturesEnum(String featureName) {
    this.featureName = featureName;
  }

  @Override
  public String toString() {
    return featureName;
  }
}
