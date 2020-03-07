package com.cookbook.data.api;

import com.cookbook.data.provider.NewRecipeSuggestionProvider;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class ApiResults {

    @SerializedName("meals")
    private List<ApiRecipe> results;

    public ApiResults(ApiRecipe[] results) {
        this.results = Arrays.asList(Arrays.copyOfRange(results, 0, NewRecipeSuggestionProvider.MAX_RESULT_SIZE));
    }

    public List<ApiRecipe> getResults() {
        return results;
    }
}
