package no.recipeheaven;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private RequestQueue volleyQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volleyQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_main);

        setupToolbar();

        getAllRecipes(new Response.Listener<Recipes>() {
            @Override
            public void onResponse(Recipes response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("RecipeHeaven");
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView(Recipes recipes) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(
                        this,                           // Context
                        LinearLayoutManager.VERTICAL,   // Orientation
                        false                           // ReverseLayout
                );

        recyclerView.setLayoutManager(layoutManager);

        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static GsonRequest<Recipes> getAllRecipes(Response.Listener<Recipes> successListener, Response.ErrorListener errorListener) {
        GsonRequest<Recipes> gamesRequest = new GsonRequest<>(
                "http://www.godt.no/api/recipes/",
                Recipes.class,
                null,
                successListener,
                errorListener
        );
        return gamesRequest;
    }
}
