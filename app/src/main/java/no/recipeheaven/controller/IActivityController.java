package no.recipeheaven.controller;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Sam Mathias Weggersen on 14/08/15.
 */
public interface IActivityController extends IContextController {

    void initializeActivity(Activity activity);
    void onCreate(Bundle savedInstanceState);
    void onPause();
    void onResume();
    void onDestroy();

}
