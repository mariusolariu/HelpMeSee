package com.example.marius.helpmesee.app_logic;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */

import android.os.Bundle;
import android.view.View;

public interface MvpView {

  View getAndroidLayoutView();

  Bundle getViewState();


}
