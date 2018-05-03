package com.example.marius.helpmesee.mainscreen.views;

import com.example.marius.helpmesee.common.MvpView;
import java.util.List;

/**
 * Created by Marius
 */

public interface MainMenuScreenView extends MvpView {


  interface MainMenuScreenListener {
    void onFeatureSelected(long id);
  }

  void displayAppFeatures(List<String> features);

  void setListener(MainMenuScreenListener listener);
}
