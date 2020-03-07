package com.cookbook.data.provider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;

public class NewRecipeSuggestionProvider extends ContentProvider {

    public static final String AUTHORITY = "com.cookbook.provider.newrecipesuggestion";
    public static final int MAX_RESULT_SIZE = 5;
    public static final String[] CURSOR_COLUMNS = new String[] {"_id", SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_INTENT_DATA};

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        if (selectionArgs != null && selectionArgs.length > 0) {
            final Context context = getContext();
            RecipeDao dao = RecipeDatabase.getInstance(context).getRecipeDao();

            Cursor rawCursor = dao.getRecipesLike("%" + selectionArgs[0] + "%");
            MatrixCursor formattedCursor = new MatrixCursor(CURSOR_COLUMNS);

            final int resultSize = rawCursor.getCount() > MAX_RESULT_SIZE ? MAX_RESULT_SIZE : rawCursor.getCount();
            rawCursor.moveToFirst();
            for (int i = 0; i < resultSize; i++) {
                formattedCursor.addRow(new Object[] {i, rawCursor.getString(0), rawCursor.getInt(1)});
                rawCursor.moveToNext();
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
