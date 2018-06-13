package com.example.marius.helpmesee.directions.model;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public enum Instruction {
  LEFT("go left"), RIGHT("go right"), STRAIGHT("continue straight"), END(
      "You arrived at destination!"),
  T_RIGHT_150("Turn right abruplty degrees soon"), T_RIGHT_90("Turn right soon"),T_RIGHT_30("Turn slightly to right soon"),
  T_LEFT_150("Turn left abruplty degrees soon"), T_LEFT_90("Turn left soon"),T_LEFT_30("Turn slightly to left soon");


  private String instruction;

  Instruction(String instruction) {
    this.instruction = instruction;
  }

  @Override
  public String toString() {
    return instruction;
  }

}
