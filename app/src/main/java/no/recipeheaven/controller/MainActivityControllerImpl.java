package no.recipeheaven.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import no.recipeheaven.R;
import no.recipeheaven.adapter.RecipeAdapter;
import no.recipeheaven.api.RestBuilder;
import no.recipeheaven.model.Recipe;
import no.recipeheaven.utils.ItemClickSupport;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Sam Mathias Weggersen on 14/08/15.
 */
public class MainActivityControllerImpl implements IActivityController {

    private Activity mActivity;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private RecipeAdapter mAdapter;

    private List<Recipe> mRecipes;

    @Override
    public void initializeActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupRecyclerView();

        mCompositeSubscription.add(subscribeRecipesList());
    }

    private Subscription subscribeRecipesList() {
        return RestBuilder.getRecipeListObservable().subscribe(new Action1<List<Recipe>>() {
            @Override
            public void call(List<Recipe> recipes) {
                mRecipes = recipes;
                mAdapter.setRecipes(recipes);
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) mActivity.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(
                        mActivity,                           // Context
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

        mAdapter = new RecipeAdapter();
        recyclerView.setAdapter(mAdapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Recipe recipe = mRecipes.get(position);
                mCompositeSubscription.add(RestBuilder.getRecipeObservable(recipe.id).subscribe(new Action1<Recipe>() {
                    @Override
                    public void call(Recipe recipe) {
                        Toast.makeText(mActivity, recipe.description, Toast.LENGTH_SHORT).show();
                    }
                }));
            }
        });
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        mCompositeSubscription.unsubscribe();
        mCompositeSubscription = null;
        mActivity = null;
        mAdapter = null;
        mRecipes = null;
    }

    @Override
    public Context getContext() {
        return mActivity;
    }
}
