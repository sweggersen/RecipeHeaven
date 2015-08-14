package no.recipeheaven.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;
import java.util.List;

import no.recipeheaven.model.Recipe;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Sam Mathias Weggersen on 14/08/15.
 */
public class RestBuilder<T> {

    private static Gson sGson;
    private static RestAdapter sRestAdapter;

    private static RecipesService sRecipesService;
    private static RecipeService sRecipeService;

    public RestBuilder() {

        if (sGson == null) {
            sGson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapter(Date.class, new DateTypeAdapter())
                    .create();
        }

        if (sRestAdapter == null) {
            sRestAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://www.godt.no/api")
                    .setConverter(new GsonConverter(sGson))
                    .build();
        }
    }

    public T build(Class<T> service) {
        return sRestAdapter.create(service);
    }

    public static Observable<List<Recipe>> getRecipeListObservable() {
        if (sRecipesService == null) {
            sRecipesService = new RestBuilder<RecipesService>().build(RecipesService.class);
        }
        return sRecipesService.listRecipes().cache()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Recipe> getRecipeObservable(long id) {
        if (sRecipeService == null) {
            sRecipeService = new RestBuilder<RecipeService>().build(RecipeService.class);
        }
        return sRecipeService.listRecipe(id).cache()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
