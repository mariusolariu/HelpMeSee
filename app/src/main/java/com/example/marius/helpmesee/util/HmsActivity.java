package com.example.marius.helpmesee.util;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.marius.helpmesee.app_logic.CommandProcessor;
import com.example.marius.helpmesee.app_logic.Constants;
import java.util.ArrayList;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 *   Provides the default implementation for speech input button. All custom listeners of this app must subclass it
 */

/**
 * Each presenter/activity of Hms should have the following methods
 */
public abstract class HmsActivity extends AppCompatActivity {

  public void startRecording() {
    try {
      startActivityForResult(Constants.SPEECH_INTENT, Constants.SPEECH_INPUT_CODE);
    } catch (Exception e) {
      Log.e(Constants.HMS_INFO,
          "Speech recognition not supported on this device!\n Method: startRecording()");
      e.printStackTrace();
    }
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case Constants.SPEECH_INPUT_CODE:
        if ((resultCode == RESULT_OK) && (data != null)) {
          ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
          String detectedText = text.get(0);
          Log.i(Constants.HMS_INFO, "Detected text:" + detectedText);

          CommandProcessor.getInstance().processCommand(detectedText, this);
        }

        break;
    }
  }

}