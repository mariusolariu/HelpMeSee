package com.example.marius.helpmesee.mainscreen;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import com.example.marius.helpmesee.R;
import com.example.marius.helpmesee.common.Constants;
import java.util.List;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */

public class MainMenuScreenViewImpl implements MainMenuScreenView {
  private MainMenuScreenListener mainMenuScreenListener;

  private View rootView; //the layout
  private ListView featuresListView;
  private Button speechInputButton;


  private Context context;
  /**
   * @param context - It allows access to application-specific resources and classes, <br> as well
   * as up-calls for application-level operations such as launching activities, <br> broadcasting
   * and receiving intents
   *
   * @param container - A ViewGroup is a special view that can contain other views (called children.) <br>
   *   The view group is the base class for layouts and views containers.
   */
  public MainMenuScreenViewImpl(Context context, ViewGroup container) {
    this.context = context;
    rootView = LayoutInflater.from(context).inflate(R.layout.main_screen_layout, container);

    featuresListView = (ListView) rootView.findViewById(R.id.featuresList);
    speechInputButton = (Button) rootView.findViewById(R.id.speechButton);

    initialize();
  }

  private void initialize() {

    //int spButtonWidth = 300 ;
    //int spButtonHeight= 200 ;

    //set button's size
    int spButtonWidth = (int) Constants.PHONE_WIDTH_DP ;
    int spButtonHeight= (int) Constants.PHONE_HEIGHT_DP / 4;

    LayoutParams layoutParams = new LayoutParams(spButtonWidth, spButtonHeight);
    layoutParams.gravity = Gravity.BOTTOM + Gravity.RIGHT;
    layoutParams.bottomMargin = (int) Constants.PHONE_HEIGHT_DP / 20;
    layoutParams.rightMargin = (int) Constants.PHONE_HEIGHT_DP / 20;

    speechInputButton.setLayoutParams(layoutParams);

    speechInputButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        mainMenuScreenListener.startRecording();
      }
    });
  }

  @Override
  public View getRootView() {
    return rootView;
  }

  @Override
  public Bundle getViewState() {
    return null;
  }


  @Override
  public void displayAppFeatures(List<String> features) {
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
    arrayAdapter.addAll(features);

    featuresListView.setAdapter(arrayAdapter);
  }

  //callback listener, notifies the controller whenever a list view item is clicked
  @Override
  public void setListener(MainMenuScreenListener listener) {
    mainMenuScreenListener = listener;

    //map interface of our custom listener with the one of ListView
    featuresListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

      @Override
      /**
       * @params position -  the id in the listview row
       * @params id - can be the id of  row in db (that's why is long)
       */
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mainMenuScreenListener != null){
          mainMenuScreenListener.onFeatureSelected(position);
        }
      }
    });
  }
}
