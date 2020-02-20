package com.cookbook.event;

import com.cookbook.model.MenuDay;

import java.util.List;

public class MenuMessageEvent {

    public List<MenuDay> days;

    public MenuMessageEvent(List<MenuDay> days) {
        this.days = days;
    }
}
