package no.recipeheaven.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import no.recipeheaven.R;
import no.recipeheaven.controller.DetailActivityControllerImpl;
import no.recipeheaven.controller.IActivityController;

public class DetailActivity extends ABaseActivity {

    IActivityController mActivityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected IActivityController setActivityController() {
        return new DetailActivityControllerImpl();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
