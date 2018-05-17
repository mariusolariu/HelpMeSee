package com.example.marius.helpmesee.app_logic;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import java.util.Locale;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public class Constants {
  public static final String HMS_INFO = "HmsInfo"; //tag used for logging info
  public static final int SPEECH_INPUT_CODE = 1;
  public static final Intent SPEECH_INTENT;

  //string constants
  public static final String ORIGIN = "origin";
  public static final String DESTINATION = "destination";
  public static final String MODE = "mode";
  public static final String WALKING = "walking";


  /**
   *   This block gets executed when the class is loaded in the memory.
   */
  static {
    SPEECH_INTENT = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    SPEECH_INTENT
        .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    String language = Locale.getDefault().getLanguage();
    Log.i(HMS_INFO, "Detected language:" + language);
    SPEECH_INTENT.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
    SPEECH_INTENT.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something...");
  }


//  public static final float PHONE_WIDTH_DP;
//  public static final float PHONE_HEIGHT_DP;
//
//  static {
//    Context currentContext = AppState.getInstance().getCurrentContext();
//    DisplayMetrics displayMetrics = currentContext.getResources().getDisplayMetrics();
//
//
//    //FIXME: these values are not the correct ones
//    PHONE_WIDTH_DP  = displayMetrics.widthPixels / displayMetrics.density;
//    PHONE_HEIGHT_DP  = displayMetrics.heightPixels / displayMetrics.density;
//  }



}
