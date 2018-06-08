package com.example.marius.helpmesee.directions.model;

import android.location.Location;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public interface DirectionsModelManager {

   void fetchInstruction(Location newLocation);

   void setModelListener(DirectionsModelListener listener);
}
