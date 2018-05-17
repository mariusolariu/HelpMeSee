package com.example.marius.helpmesee.directions.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.example.marius.helpmesee.R;
import com.example.marius.helpmesee.app_logic.AppState;
import com.example.marius.helpmesee.directions.presenter.DirectionsScreenListener;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public class DirectionsScreenViewImpl implements DirectionsScreenView {

  private Context context;
  private DirectionsScreenListener screenListener;

  private View rootView; //layout
  private Button speechInputButton;
  private Button findPathButton;
  private EditText destActv;
  private TextView distanceTV;
  private TextView durationTV;

  public DirectionsScreenViewImpl(Context context, ViewGroup container) {
    this.context = context;
    rootView = LayoutInflater.from(context).inflate(R.layout.directions_screen_layout, container);

    initialize();
    setButtonsSize();
    addListenersToButtons();
  }

  private void initialize() {
    speechInputButton = (Button) rootView.findViewById(R.id.speechButton_directions);
    findPathButton = (Button) rootView.findViewById(R.id.findPathB);
    destActv = (EditText) rootView.findViewById(R.id.destACTV);
    distanceTV = (TextView) rootView.findViewById(R.id.distanceTV);
    durationTV = (TextView) rootView.findViewById(R.id.durationTV);
  }

  private void addListenersToButtons() {
    findPathButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isDesinationSet()) {
          String destination = destActv.getText().toString();
          screenListener.findPath(destination);
        }
      }
    });

    speechInputButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        screenListener.startRecording();
      }
    });

  }

  private void setButtonsSize() {
    LayoutParams params = AppState.getInstance().getSpeechButtonLayoutParams();
    speechInputButton.setLayoutParams(params);

    //TODO: should be done only once when app is first run
    int phone_height_dp = AppState.getInstance().getPhone_height_dp();
    int phone_width_dp = AppState.getInstance().getPhone_width_dp();
    int spButtonWidth = (int) (phone_width_dp / 1.2);
    int spButtonHeight = phone_height_dp / 4;

    LayoutParams layoutParamsFP = new LayoutParams(spButtonWidth, spButtonHeight);
    layoutParamsFP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    layoutParamsFP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    layoutParamsFP.bottomMargin = phone_height_dp / 20;
    layoutParamsFP.rightMargin = phone_height_dp / 20;
    layoutParamsFP.leftMargin = phone_height_dp / 20;
    layoutParamsFP.topMargin = phone_height_dp / 20;

    findPathButton.setLayoutParams(layoutParamsFP);
  }


  @Override
  public View getAndroidLayoutView() {
    return rootView;
  }

  @Override
  public Bundle getViewState() {
    return null;
  }

  private boolean isDesinationSet() {
    boolean valid = true;

    String destination = destActv.getText().toString();

    if (TextUtils.isEmpty(destination)) {
      destActv.setError("Required!");
      valid = false;
    }

    return valid;
  }

  @Override
  public void setScreenListener(DirectionsScreenListener listener) {
    screenListener = listener;
  }

  @Override
  //this method will set the text decoded from speech input
  public void setDestination(String destination) {
    destActv.setText(destination);
  }

  @Override
  public void setDistance(String distance) {
    distanceTV.setText(distance);
  }

  @Override
  public void setDuration(String duration) {
    durationTV.setText(duration);
  }

}