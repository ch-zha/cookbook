package com.cookbook.data.provider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cookbook.data.api.ApiRecipe;
import com.cookbook.data.api.ApiRecipeGsonAdapter;
import com.cookbook.data.api.ApiResults;
import com.cookbook.data.api.MealApi;
import com.cookbook.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewRecipeSuggestionProvider extends ContentProvider {

    public static final String AUTHORITY = "com.cookbook.provider.newrecipesuggestion";
    public static final int MAX_RESULT_SIZE = 5;
    public static final String API_ID_EXTRA = "api_id";
    public static final String[] CURSOR_COLUMNS = new String[] {
            "_id",
            SearchManager.SUGGEST_COLUMN_TEXT_1,
            SearchManager.SUGGEST_COLUMN_INTENT_DATA,
            SearchManager.EXTRA_DATA_KEY,
            SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA
    };

    private static Retrofit retrofit;

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        if (selectionArgs != null && selectionArgs.length > 0) {

            if (retrofit == null) {

                retrofit = new Retrofit.Builder()
                        .baseUrl(getContext().getString(R.string.mealdb_base_url))
                        //Not using custom type converter here since only name & id are needed
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }

            MealApi api = retrofit.create(MealApi.class);
            Call<ApiResults> call = api.getSearchResults(getContext().getString(R.string.mealdb_api_key), selectionArgs[0]);
            Log.d("api_result", call.request().url().toString());
            MatrixCursor formattedCursor = new MatrixCursor(CURSOR_COLUMNS);

            try {
                ApiResults results = call.execute().body();
                if (results != null) {
                    List<ApiRecipe> resultList = results.getResults();
                    for (int i = 0; i < resultList.size(); i++) {
                        formattedCursor.addRow(new Object[]{
                                i,
                                resultList.get(i).getMealName(),
                                resultList.get(i).getMealName(),
                                API_ID_EXTRA, //TODO find out why this doesn't set the key name?
                                resultList.get(i).getMealId()
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return formattedCursor;
        }
        return null;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/vnd.com.cookbook.provider.newrecipesuggestion.mealdb";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
