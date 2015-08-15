package no.recipeheaven.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import no.recipeheaven.BR;
import no.recipeheaven.R;
import no.recipeheaven.model.Recipe;

/**
 * Created by Sam Mathias Weggersen on 14/08/15.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes = new ArrayList<>();

    public void addRecipes(List<Recipe> recipes) {
        this.recipes.addAll(recipes);

        notifyDataSetChanged();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mBinding;

        public RecipeViewHolder(View v) {
            super(v);
            mBinding = DataBindingUtil.bind(v);
        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }

    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(
                                R.layout.view_card_item,
                                parent,
                                false
                        )
        );
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder h, int position) {
        Recipe recipe = recipes.get(position);
        h.getBinding().setVariable(BR.recipe, recipe);
        h.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

}
