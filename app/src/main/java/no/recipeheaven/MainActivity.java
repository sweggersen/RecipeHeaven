package no.recipeheaven;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

        volleyQueue.add(getAllRecipes(new Response.Listener<Recipe[]>() {
            @Override
            public void onResponse(Recipe[] response) {
                setupRecyclerView(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                System.out.println(error.toString());
            }
        }));
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("RecipeHeaven");
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView(Recipe[] recipes) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(
                        this,                           // Context
                        LinearLayoutManager.VERTICAL,   // Orientation
                        false                           // ReverseLayout
                );

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int p = (int) view.getResources().getDimension(R.dimen.recycler_view_spacing);
                outRect.set(p, p, p, p);
            }
        });

        recyclerView.setAdapter(new RecipeAdapter(recipes));
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

    public static GsonRequest<Recipe[]> getAllRecipes(Response.Listener<Recipe[]> successListener, Response.ErrorListener errorListener) {
        return new GsonRequest<>(
                "http://www.godt.no/api/recipes/",
                Recipe[].class,
                null,
                successListener,
                errorListener
        );
    }
}
