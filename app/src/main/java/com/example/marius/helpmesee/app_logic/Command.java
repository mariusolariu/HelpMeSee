package com.example.marius.helpmesee.app_logic;

import android.content.Context;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */

//TODO implement command design pattern
public class Command {

  private String id;
  private Context sourceContext;
  private Class<?> destinationActivity;

  private Command() {
  }

  public String getId() {
    return id;
  }

  public Context getSourceContext() {
    return sourceContext;
  }

  public Class<?> getDestinationActivity() {
    return destinationActivity;
  }

  /**
   * non-static inner class requires an instance of outer class in order to be instantiated
   */
  public static class CommandBuilder {

    private String id;
    private Context sourceContext;
    private Class<?> destinationActivity;


    public CommandBuilder() {

    }

    public CommandBuilder setId(String id) {
      this.id = id;
      return this;
    }

    public CommandBuilder setSourceContext(Context sourceContext) {
      this.sourceContext = sourceContext;
      return this;
    }

    public CommandBuilder setDestinationActivity(Class<?> destinationActivity) {
      this.destinationActivity = destinationActivity;
      return this;
    }

    public Command build() {
      Command newCommand = new Command();

      newCommand.id = this.id;
      newCommand.sourceContext = this.sourceContext;
      newCommand.destinationActivity = this.destinationActivity;

      return newCommand;
    }
  }
}
