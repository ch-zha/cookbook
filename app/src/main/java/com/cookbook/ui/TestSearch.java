package com.cookbook.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.Nullable;

import com.example.cookbook.R;

public class TestSearch extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_widget);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = findViewById(R.id.search_widget);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }
}
