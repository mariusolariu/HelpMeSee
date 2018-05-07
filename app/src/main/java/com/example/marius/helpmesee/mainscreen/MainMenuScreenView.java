package com.example.marius.helpmesee.mainscreen;

import com.example.marius.helpmesee.common.MvpView;
import java.util.List;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */

public interface MainMenuScreenView extends MvpView {

  void displayAppFeatures(List<String> features);

  void setListener(MainMenuScreenListener listener);
}
