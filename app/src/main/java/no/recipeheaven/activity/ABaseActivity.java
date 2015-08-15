package no.recipeheaven.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import no.recipeheaven.controller.IActivityController;
import no.recipeheaven.controller.MainActivityControllerImpl;

public abstract class ABaseActivity extends AppCompatActivity {

    private IActivityController mActivityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityController = new MainActivityControllerImpl();
        mActivityController.initializeActivity(this);
    }

    protected abstract IActivityController setActivityController();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mActivityController.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityController.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActivityController.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityController.onDestroy();
    }
}
