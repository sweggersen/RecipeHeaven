package no.recipeheaven.api;

import no.recipeheaven.model.Recipe;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Sam Mathias Weggersen on 14/08/15.
 */
public interface RecipeService {
    @GET("/recipes/{id}")
    Observable<Recipe> listRecipe(@Path("id") long id);
}