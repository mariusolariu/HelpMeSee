package com.example.marius.helpmesee.directions.presenter;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import com.example.marius.helpmesee.app_logic.Constants;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Standalone methods that provide functionality
 */
public class DirectionsHelper {


  /**
   * Converts minutes into Xh-Ym-Zs format
   */
  public static String prettyReadDuration(float timeM) {
    StringBuilder duration = new StringBuilder();

    int hours = (int) (timeM / 60);
    if (hours != 0) {
      duration.append(hours).append("h");
      timeM -= hours * 60;
    }

    int minutes = (int) timeM;

    if (minutes != 0) {
      duration.append(minutes).append("m");
      timeM -= minutes;
    }

    int seconds = (int) (timeM * 60);

    if (seconds != 0) {
      duration.append(seconds).append("s");
    }

    return duration.toString();
  }

  /**
   */
  public Address getAdressFromLocation(Location location, Context context) {
    Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

    try {
      List<Address> addresses = geocoder
          .getFromLocation(location.getLatitude(), location.getLongitude(), 1);

      if (addresses.size() > 0) {
        Address address = addresses.get(0);
        Log.i(Constants.HMS_INFO, "Detected current address: " + address);
        return address;
      }

    } catch (IOException e) {
      e.printStackTrace();
      Log.d(Constants.HMS_INFO, "Couldn't reverse geocode from location: " + location);
    }

    return null;
  }
}
