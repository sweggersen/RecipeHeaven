package no.recipeheaven.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import no.recipeheaven.model.Recipe;
import no.recipeheaven.util.Consts;
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

    public RestBuilder() {

        if (sGson == null) {
            sGson = new GsonBuilder()
                    .setDateFormat(Consts.DEFAULT_DATE_FORMAT)
                    .create();
        }

        if (sRestAdapter == null) {
            sRestAdapter = new RestAdapter.Builder()
                    .setEndpoint(Consts.API_BASE_PATH)
                    .setConverter(new GsonConverter(sGson))
                    .build();
        }
    }

    public T build(Class<T> service) {
        return sRestAdapter.create(service);
    }

    @SuppressWarnings("unchecked")
    public static Observable<List<Recipe>> getRecipeListObservable(int offset, int limit) {
        if (sRecipesService == null) {
            sRecipesService = new RestBuilder<RecipesService>().build(RecipesService.class);
        }
        return addCacheAndThreads(sRecipesService.listRecipes(offset, limit, false));
    }

    @SuppressWarnings("unchecked")
    public static Observable<Recipe> getRecipeObservable(long id) {
        if (sRecipesService == null) {
            sRecipesService = new RestBuilder<RecipesService>().build(RecipesService.class);
        }
        return addCacheAndThreads(sRecipesService.listRecipe(id));
    }

    private static Observable addCacheAndThreads(Observable observable) {
        return observable.cache()
//                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
