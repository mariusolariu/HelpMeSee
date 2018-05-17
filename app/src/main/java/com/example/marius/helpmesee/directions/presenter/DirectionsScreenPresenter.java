package com.example.marius.helpmesee.directions.presenter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.example.marius.helpmesee.R;
import com.example.marius.helpmesee.directions.model.Path;
import com.example.marius.helpmesee.directions.view.DirectionsScreenView;
import com.example.marius.helpmesee.directions.view.DirectionsScreenViewImpl;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.List;


/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */

public class DirectionsScreenPresenter extends DirectionsScreenListener implements
    OnMapReadyCallback, PathFoundListener {

  private DirectionsScreenView rootView;

  //constants
  private static final int DEFAULT_ZOOM = 15;
  // Keys for storing activity state.
  private static final String KEY_CAMERA_POSITION = "camera_position";
  private static final String KEY_LOCATION = "location";
  private static final String DIRECTIONS_SCREEN_TAG = "DirectionsScreen";


  //interaction with map object
  private LocationRequest locationRequest;
  private GoogleMap googleMap;
  private boolean requestingLocationUpdates = true;
  private LocationCallback locationCallback;
  // The entry point to the Fused Location Provider.
  private FusedLocationProviderClient fusedLocationProviderClient;

  //logic
  private Location currentUserLocation;
  private Address currentUserAddress;
  private PathProvider pathProvider;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    rootView = new DirectionsScreenViewImpl(this, null);
    rootView.setScreenListener(this);
    setContentView(rootView.getAndroidLayoutView());

    /**
     * A Fragment is a piece of an application's user interface or behavior that can be placed in an Activity <br>
     *   Note: isn't this a breaking of the MVP architecture?
     *   https://developer.android.com/reference/android/app/Fragment
     *   1 dirtiness point
     * Note: I could move the part where I get the fragment into the view but then I will end up with
     *       a dependency on the there on the activity (i.e. 1 dirtiness point)
     *       https://www.techyourchance.com/activities-android/
     */
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

//    createLocationRequest();
//
//    locationCallback = new LocationCallback() {
//      @Override
//      public void onLocationResult(LocationResult locationResult) {
//        if (locationResult == null) {
//          return;
//        }
//        for (Location location : locationResult.getLocations()) {
//          Log.i(DIRECTIONS_SCREEN_TAG, location.getTime() + " (" + location.getLatitude() + ", " + location.getLongitude());
//        }
//      };
//    };
    initialize();
  }

  private void initialize() {
    pathProvider = new PathProvider(this);
  }

  /**
   * Saves the state of the map when the activity is paused.
   */
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    if (googleMap != null) {
      outState.putParcelable(KEY_CAMERA_POSITION, googleMap.getCameraPosition());
      outState.putParcelable(KEY_LOCATION, currentUserLocation);
      super.onSaveInstanceState(outState);
    }
  }


  @SuppressLint("MissingPermission")
  @Override
  /**
   * Permision check is done when the app is launched
   */
  public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;

    googleMap.setMyLocationEnabled(true);
    googleMap.getUiSettings().setMyLocationButtonEnabled(true);

    getDeviceLocation();
  }

//  @Override
//  protected void onResume() {
//    super.onResume();
//
//    if (requestingLocationUpdates) {
//      startLocationUpdates();
//    }
//  }
//
//  private void startLocationUpdates() {
//    if (checkLocationPermission()) {
//      fusedLocationProviderClient.requestLocationUpdates(locationRequest,
//          locationCallback,
//          null/* Looper */);
//    }
//  }
//
//  protected void onPause() {
//    super.onPause();
//    stopLocationUpdates();
//  }
//
//
//
//  private void stopLocationUpdates() {
//    fusedLocationProviderClient.removeLocationUpdates(locationCallback);
//  }

//  private class LooperThread extends Thread {
//    public Handler mHandler;
//
//    public void run() {
//      Looper.prepare();
//
//      mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//          // process incoming messages
//          Log.i(DIRECTIONS_SCREEN_TAG, "handleMessage: " + msg.getWhen());
//        }
//      };
//
//      Looper.loop();
//    }
//  }

  /**
   * Gets the current location of the device
   */
  private void getDeviceLocation() {
    try {
      Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
      locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
        @Override
        public void onComplete(@NonNull Task<Location> task) {
          if (task.isSuccessful()) {
            // Set the map's camera position to the current location of the device.
            currentUserLocation = task.getResult();
            final double latitude = currentUserLocation.getLatitude();
            final double longitude = currentUserLocation.getLongitude();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude,
                    longitude), DEFAULT_ZOOM));

            Log.i(DIRECTIONS_SCREEN_TAG,
                "User location: (" + latitude + ", " + longitude + ")");

          } else {
            Log.e(DIRECTIONS_SCREEN_TAG, "Exception: %s", task.getException());
          }
        }
      });
    } catch (SecurityException e) {
      Log.e(DIRECTIONS_SCREEN_TAG, e.getMessage());
    }
  }

  @Override
  public void findPath(final String destination) {
    //get the path
    //send message to view to draw it
    //update device location
    try {
      Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
      locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
        @Override
        public void onComplete(@NonNull Task<Location> task) {
          if (task.isSuccessful()) {
            // Set the map's camera position to the current location of the device.
            currentUserLocation = task.getResult();
            final double latitude = currentUserLocation.getLatitude();
            final double longitude = currentUserLocation.getLongitude();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude,
                    longitude), DEFAULT_ZOOM));

            Log.i(DIRECTIONS_SCREEN_TAG,
                "User location before computing the path: (" + latitude + ", " + longitude + ")");

            pathProvider.setDestination(destination);

            final String originLatLng =
                latitude + "," + longitude;
            pathProvider.setOrigin(originLatLng);

            pathProvider.start();

          } else {
            Log.e(DIRECTIONS_SCREEN_TAG, "Exception: %s", task.getException());
          }
        }
      });
    } catch (SecurityException e) {
      Log.e(DIRECTIONS_SCREEN_TAG, e.getMessage());
    }

  }

  @Override
  public void pathsFound(List<com.example.marius.helpmesee.directions.model.Path> paths) {
    if (paths != null && paths.size() > 0) {
      Path mainPath = paths.get(0);

      String duration = DirectionsHelper.prettyReadDuration(mainPath.getTimeM());
      rootView.setDuration(duration);

      String distance = String.valueOf(mainPath.getDistanceKM()) + "km";
      rootView.setDistance(distance);

      drawPathOnGoogleMap(mainPath.getCoordinatesLatLng());
    } else {
      Log.e(DIRECTIONS_SCREEN_TAG, "Couldn't find a valid path!");
      Toast.makeText(this, "Couldn't find a valid path! Please try another destination!",
          Toast.LENGTH_SHORT).show();
    }
  }

  private void drawPathOnGoogleMap(List<LatLng> coordinatesLatLng) {

    googleMap.clear();

    PolylineOptions polylineOptions = new PolylineOptions()
        .geodesic(true)
        .color(Color.BLUE)
        .width(10);

    for (LatLng p : coordinatesLatLng) {
      polylineOptions.add(p);
    }

    googleMap.addPolyline(polylineOptions);
    final LatLng destinationLatLng = coordinatesLatLng.get(coordinatesLatLng.size() - 1);

    //add finish marker - icon: a flag
    googleMap.addMarker(new MarkerOptions()
        .position(destinationLatLng)
        .icon(BitmapDescriptorFactory
            .fromResource(R.mipmap.stop_flag)));
  }

  /**
   * A fast update interval of 5000 milliseconds causes <br>
   * the fused location provider to return location updates that are accurate to <br>
   * within A FEW FEET.
   */
//  protected void createLocationRequest() {
//    locationRequest = new LocationRequest();
//    locationRequest.setInterval(7000);
//    locationRequest.setFastestInterval(5000);
//    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//        .addLocationRequest(locationRequest);
//    SettingsClient client = LocationServices.getSettingsClient(this);
//    Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
//    task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
//      @Override
//      public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//        // All location settings are satisfied. The client can initialize
//        // location requests here.
//        // ...
//            Log.i(DIRECTIONS_SCREEN_TAG, "Location settings are ok!");
//      }
//    });

//    task.addOnFailureListener(this, new OnFailureListener() {
//      @Override
//      public void onFailure(@NonNull Exception e) {
//        if (e instanceof ResolvableApiException) {
//          // Location settings are not satisfied, but this can be fixed
//          // by showing the user a dialog.
//          try {
//            // Show the dialog by calling startResolutionForResult(),
//            // and check the result in onActivityResult().
//            ResolvableApiException resolvable = (ResolvableApiException) e;
//            resolvable.startResolutionForResult(DirectionsScreenPresenter.this,
//                REQUEST_CHECK_SETTINGS);
//          } catch (IntentSender.SendIntentException sendEx) {
//            // Ignore the error.
//          }
//        }
//      }
//    });
//
//  }
}