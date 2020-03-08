package com.cookbook.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MealApi {

    @GET("{key}/search.php")
    Call<ApiResults> getSearchResults(@Path("key") String key, @Query("s") String recipe_name);

    @GET("{key}/lookup.php")
    Call<ApiRecipe> getRecipe(@Path("key") String key, @Query("i") String id);

}
