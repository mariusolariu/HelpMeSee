package com.example.marius.helpmesee.directions.presenter;


import java.util.List;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public interface PathFoundListener {

  /**
   * Callback method
   * @param path - dto object
   */
  void pathFound(List<com.example.marius.helpmesee.directions.model.Path> path);
}
