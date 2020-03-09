package com.cookbook.data.entity;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String name;

    @Nullable
    private String thumb = "";

    public Recipe(int id, String name, String thumb) {
        this.id = id;
        this.name = name;
        this.thumb = thumb;
    }

    @Ignore
    public Recipe(String name, String thumb) {
        this.name = name;
        this.thumb = thumb;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumb() {
        return thumb;
    }

}
