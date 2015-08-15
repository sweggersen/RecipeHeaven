package no.recipeheaven.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import no.recipeheaven.R;
import no.recipeheaven.adapter.RecipeAdapter;
import no.recipeheaven.api.RestBuilder;
import no.recipeheaven.model.Recipe;
import no.recipeheaven.util.EndlessRecyclerOnScrollListener;
import no.recipeheaven.util.ItemClickSupport;
import no.recipeheaven.util.RecyclerViewPositionHelper;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Sam Mathias Weggersen on 14/08/15.
 */
public class MainActivityControllerImpl implements IActivityController {

    public static final int RECIPE_LIMIT = 9;

    private Activity mActivity;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private RecipeAdapter mAdapter;
    private SwipyRefreshLayout mSwipeRefreshLayout;

    private List<Recipe> mRecipes = new ArrayList<>();

    @Override
    public void initializeActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupRecyclerView();

        mCompositeSubscription.add(subscribeRecipesList(0));
    }

    private synchronized Subscription subscribeRecipesList(int offset) {
        mSwipeRefreshLayout.setRefreshing(true);
        return RestBuilder.getRecipeListObservable(offset, RECIPE_LIMIT)
                .subscribe(new Action1<List<Recipe>>() {
                    @Override
                    public synchronized void call(List<Recipe> recipes) {
                        mRecipes.addAll(recipes);
                        mAdapter.addRecipes(recipes);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void setupRecyclerView() {
        mSwipeRefreshLayout = (SwipyRefreshLayout) mActivity.findViewById(R.id.swipyrefreshlayout);
        mSwipeRefreshLayout.setColorSchemeColors(mActivity.getResources().getColor(R.color.primary));;

        RecyclerView recyclerView = (RecyclerView) mActivity.findViewById(R.id.recyclerview);

        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

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
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(RecyclerViewPositionHelper.createHelper(recyclerView)) {
            @Override
            public void onLoadMore(int currentPage) {
                mCompositeSubscription.add(subscribeRecipesList(currentPage * RECIPE_LIMIT));
            }
        });

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
