package com.example.marius.helpmesee.mainscreen.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.marius.helpmesee.R;
import java.util.List;

/**
 * Created by Marius
 */

public class MainMenuScreenViewImpl implements MainMenuScreenView {

  private View rootView; //the layout
  private ListView featuresListView;
  private MainMenuScreenListener mainMenuScreenListener;
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
