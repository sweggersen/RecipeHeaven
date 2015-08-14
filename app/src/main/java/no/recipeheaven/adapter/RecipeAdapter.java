package no.recipeheaven.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import no.recipeheaven.R;
import no.recipeheaven.model.Recipe;

/**
 * Created by Sam Mathias Weggersen on 14/08/15.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes = new ArrayList<>();

    public void setRecipes(List<Recipe> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);

        notifyDataSetChanged();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView prepTime;
        public TextView numComments;
        public TextView numLikes;
        public TextView title;

        public RecipeViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            prepTime = (TextView) v.findViewById(R.id.prep_time);
            numComments = (TextView) v.findViewById(R.id.comments);
            numLikes = (TextView) v.findViewById(R.id.likes);
            title = (TextView) v.findViewById(R.id.title);
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

        Glide.with(h.itemView.getContext())
                .load(recipe.image)
                .into(h.image);

        h.numComments.setText(
                String.valueOf(recipe.numberOfComments)
        );

        h.prepTime.setText(
                String.valueOf(recipe.preparationTime) + " min"
        );

        h.numLikes.setText(
                String.valueOf(recipe.numberOfLikes)
        );

        h.title.setText(recipe.title);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

}
