package no.recipeheaven.api;

import java.util.List;

import no.recipeheaven.model.Recipe;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Sam Mathias Weggersen on 14/08/15.
 */
public interface RecipesService {

    @GET("/recipes/")
    Observable<List<Recipe>> listRecipes();

    @GET("/recipes/{id}")
    Observable<Recipe> listRecipe(@Path("id") long id);

}