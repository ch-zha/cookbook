package com.cookbook.event;

import org.greenrobot.eventbus.EventBus;

public class RequestRecipeEvent {

    public int recipe_id;

    public RequestRecipeEvent(int id) {
        this.recipe_id = id;
    }

}
