package com.cookbook.data;

public class Repository {

    private RecipeDao recipe_dao;

    public Repository(RecipeDao recipe_dao) {
        this.recipe_dao = recipe_dao;
    }

}
