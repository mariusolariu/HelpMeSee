package com.example.marius.helpmesee.directions.model;

import com.google.android.gms.maps.model.LatLng;
import java.util.List;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
//DTO object - part of the model
public class Path {

  private String origin;
  private String destination;
  private LatLng originLatLng;
  private LatLng destinationLatLng;
  private float distanceKM; //in km
  private float timeM; //in minutes

  private List<LatLng> coordinatesLatLng;

  public Path(String origin, String destination,
      LatLng originLatLng, LatLng destinationLatLng, float distanceKM, float timeM,
      List<LatLng> coordinatesLatLng) {
    this.origin = origin;
    this.destination = destination;
    this.originLatLng = originLatLng;
    this.destinationLatLng = destinationLatLng;
    this.distanceKM = distanceKM;
    this.timeM = timeM;
    this.coordinatesLatLng = coordinatesLatLng;
  }

  public String getOrigin() {
    return origin;
  }

  public String getDestination() {
    return destination;
  }

  public LatLng getOriginLatLng() {
    return originLatLng;
  }

  public LatLng getDestinationLatLng() {
    return destinationLatLng;
  }

  public float getDistanceKM() {
    return distanceKM;
  }

  public float getTimeM() {
    return timeM;
  }

  public List<LatLng> getCoordinatesLatLng() {
    return coordinatesLatLng;
  }
}
