package com.example.marius.helpmesee.directions.model;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public class InstructionProviderTask extends AsyncTask<Object, Void, Instruction> {

  private InstructionConsumer instructionConsumer;
  private LatLng userLocation;
  private Float speedMph;
  private Float bearing;
  private Float pathRadiusMeters;
  private List<GCSPoint> pathCoordinates;
  private GCSPoint segmentStart;
  private GCSPoint segmentEnd;
  private GCSPoint predictedFutureLocation;
  private GCSPoint normalPoint;

  /**
   * Target is chosen to be at maximum NP_FL_DISTANCE_THRESHOLD meters in front of normalPoint
   */
  private final double NP_FL_DISTANCE_THRESHOLD = 7;

  public interface InstructionConsumer {

    void instructionResult(Instruction instruction);
  }


  public InstructionProviderTask(InstructionConsumer instructionConsumer) {
    this.instructionConsumer = instructionConsumer;
  }

  @Override
  /**
   *   Implementation of Craig Rynolds path folowing algorithm addpted by me to Geographic Coordinate System
   *   https://www.red3d.com/cwr/steer/PathFollow.html
   */
  //FIXME Since the whole logic uses LatLng in background, convert from GCSPoint back to LatLng
  protected Instruction doInBackground(Object... objects) {
    userLocation = (LatLng) objects[0];
    speedMph = (Float) objects[1];
    bearing = (Float) objects[2];
    pathRadiusMeters = (Float) objects[3];

    pathCoordinates = null;
    try {
      pathCoordinates = (List<GCSPoint>) objects[4];
    } catch (ClassCastException e) {
      e.printStackTrace();
    }

    segmentStart = new GCSPoint(0, 0);
    segmentEnd = new GCSPoint(0, 0);

    //step 1: predict user's future location
    predictedFutureLocation = GCSPoint
        .predictFutureLocation(userLocation, bearing, speedMph,
            3f);

    //step 2: find the normal point along the path
    normalPoint = findNormalPoint();

    //step 3: choose a target
    GCSPoint target = new GCSPoint(0, 0);
    if (!normalPoint.equals(
        segmentEnd)) { //normalPoint is segmentEnd then there is no "normal point" for predictedFutureLocation on segment
      target = chooseTarget();
    } else {
      target = normalPoint;
    }

    double distanceU_SegEnd = GCSPoint
        .distanceBetweenPoints(userLocation.latitude, userLocation.longitude,
            segmentEnd.getLatitude(), segmentEnd.getLongitude());

    /*
     * when user approaches the end of a segment/end of journey notify him with special instruction
     */
    if ((distanceU_SegEnd >= 1) && (distanceU_SegEnd <= 5)) {
      return notifyUser();
    }

    //step 4: notify the user with an instruction if necessary
    double distanceFL_NP = GCSPoint.distanceBetweenPoints(predictedFutureLocation, normalPoint);
    //Log.i(Constants.HMS_INFO, "future - np: " + distanceFL_NP);

    Instruction instruction = Instruction.STRAIGHT;
    if (distanceFL_NP > pathRadiusMeters) { //give instruction to user
      instruction = chooseInstruction(target);
    }
//
//    return new SimpleDto(userLocation, predictedFutureLocation, normalPoint, target,
//        segmentStart, segmentEnd, instruction);

    return instruction;
  }


  /**
   * Notifies the user about a turn or the end of journey
   */
  private Instruction notifyUser() {
    int indexSegmentEnd = pathCoordinates.indexOf(segmentEnd);

    //end of journey
    if (indexSegmentEnd == pathCoordinates.size() - 1) {

      return Instruction.END;
//      return new SimpleDto(userLocation, predictedFutureLocation, normalPoint, target, segmentStart,
//          segmentEnd, Instruction.END);
    }

    //detect on which side of the user is the next segment to walk
    GCSPoint nextSegmentEnd = pathCoordinates.get(indexSegmentEnd + 1);
    int userSideToNextSegment = GCSPoint
        .detectPointSide(userLocation.latitude, userLocation.longitude, segmentEnd,
            nextSegmentEnd);
    int angle = GCSPoint.angleBetween(segmentStart, segmentEnd, nextSegmentEnd);

//    Log.i(Constants.HMS_INFO,
//        "\nSegment start: " + segmentStart + "\n SegmentEnd:" + segmentEnd + "\n NextSegmentEnd:"
//            + nextSegmentEnd + "\nangle: " + angle);

//    if (userSideToNextSegment == GCSPoint.RIGHT) {
//      //left instruction
//      return new SimpleDto(userLocation, predictedFutureLocation, normalPoint, target, segmentStart,
//          segmentEnd, Instruction.T_RIGHT_150);
//    } else {
//      return new SimpleDto(userLocation, predictedFutureLocation, normalPoint, target, segmentStart,
//          segmentEnd, Instruction.T_LEFT_90);
//    }

    switch (userSideToNextSegment) {
      case GCSPoint.RIGHT:
        return giveRightSideInstructions(angle);

      //left
      default:
        return giveLeftSideInstructions(angle);
    }
  }

  private Instruction giveRightSideInstructions(int angle) {
    Instruction resultInstr = null;
//    Log.i(Constants.HMS_INFO, "Right: " + angle);

    if (isWithin(0, 30, angle)) {
      resultInstr = Instruction.T_RIGHT_150;
    }

    if (isWithin(31, 60, angle)) {
      resultInstr = Instruction.T_RIGHT_120;
    }

    if (isWithin(61, 140, angle)) {
      resultInstr = Instruction.T_RIGHT_90;
    }

    if (isWithin(141, 160, angle)) {
      resultInstr = Instruction.T_RIGHT_140;
    }

    if (isWithin(161, 180, angle)) {
      resultInstr = Instruction.STRAIGHT;
    }

//    return new SimpleDto(userLocation, predictedFutureLocation, normalPoint, target, segmentStart,
//        segmentEnd, resultInstr);
    return resultInstr;
  }

  private Instruction giveLeftSideInstructions(int angle) {
    Instruction resultInstr = null;

//    Log.i(Constants.HMS_INFO, "Left: " + angle);

    if (isWithin(0, 30, angle)) {
      resultInstr = Instruction.T_LEFT_150;
    }

    if (isWithin(31, 60, angle)) {
      resultInstr = Instruction.T_LEFT_120;
    }

    if (isWithin(61, 140, angle)) {
      resultInstr = Instruction.T_LEFT_90;
    }

    if (isWithin(141, 160, angle)) {
      resultInstr = Instruction.T_LEFT_140;
    }

    if (isWithin(161, 180, angle)) {
      resultInstr = Instruction.STRAIGHT;
    }

    return resultInstr;
  }


  private boolean isWithin(int start, int end, int x) {
    return (start <= x) && (x <= end);
  }


  @NonNull
  private Instruction chooseInstruction(
      GCSPoint target) {
    Instruction instruction;
    int userSide = GCSPoint.detectPointSide(segmentStart, segmentEnd, predictedFutureLocation);
    //TODO adjust this part to take into account this distance
    double d1 = GCSPoint.distanceBetweenPoints(predictedFutureLocation, target);

//    Log.i(Constants.HMS_INFO, "Dist bet target and future loc: " + d1);

    if (userSide == GCSPoint.RIGHT) {
      instruction = Instruction.LEFT;
    } else {
      instruction = Instruction.RIGHT;
    }
    return instruction;
  }


  private GCSPoint chooseTarget() {
    GCSPoint target = new GCSPoint(0, 0);
    //the target is chosen to be closer or further in concordance to
    //how much the user is going to be off the path
    double nP_to_FL_distance = GCSPoint
        .distanceBetweenPoints(normalPoint, predictedFutureLocation);
//    double distanceMeters = 3; //Target is chosen to be some meters after the normal point
    //chose the target to be 3 meters in front if possible, if not target is the end of segment

    double bearing = GCSPoint.computeBearingSegment(segmentStart, segmentEnd);

    target = GCSPoint
        .computeDestinatioinPoint(normalPoint, bearing, nP_to_FL_distance);

    double d1 = GCSPoint.distanceBetweenPoints(segmentStart, segmentEnd);
    double d2 = GCSPoint.distanceBetweenPoints(segmentStart, target);

    // the chosen target is not on the current segment, thus we pick segment end as target
    //this situation might occur at the end of segments
    if (d2 > d1) {
      target = segmentEnd;
    }

    return target;
  }


  /**
   * The normal point chosen is the one to which there is the smallest distance from predicted location
   * and it is on a segment from the path.
   * Also this function returns the segment end and segment start for which the normal point was found
   */
  private GCSPoint findNormalPoint() {
    GCSPoint optimumNormalPoint = null;

    double minDistance = Double.MAX_VALUE;

    int size = pathCoordinates.size();
    for (int i = 0; i < size - 1; i++) {
      GCSPoint a = pathCoordinates.get(i);
      GCSPoint b = pathCoordinates.get(i + 1);
      GCSPoint normalPoint = GCSPoint.computeNormalPoint(predictedFutureLocation, a,
          b);

      //if the normalPoint isn't on the ab segment
      // then we take the end of segment as the "closest" point from predictedFutureLocation to segment ab
      if (normalPoint == null) {
        normalPoint = b;
      }

      double distance = GCSPoint.distanceBetweenPoints(predictedFutureLocation, normalPoint);

      if (distance < minDistance) {
        minDistance = distance;
        optimumNormalPoint = normalPoint;
        segmentStart.setLatitude(a.getLatitude());
        segmentStart.setLongitude(a.getLongitude());
        segmentEnd.setLatitude(b.getLatitude());
        segmentEnd.setLongitude(b.getLongitude());
      }
    }

    return optimumNormalPoint;
  }


  @Override
  protected void onPostExecute(Instruction instruction) {
    instructionConsumer.instructionResult(instruction);
  }

}
