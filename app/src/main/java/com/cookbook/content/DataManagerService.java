package com.cookbook.content;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.cookbook.event.MenuMessageEvent;
import com.cookbook.model.MenuDay;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DataManagerService extends Service {

    private List<MenuDay> planner = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        int ret = super.onStartCommand(intent, flags, startId);
        System.out.println("\n\nStarting service\n\n");
        return startId;
    }

    @Override
    public void onCreate() {
        System.out.println("\n\nCreating service\n\n");
        sendPlannerEvent();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendPlannerEvent() {
        this.planner = new ArrayList<>();
        this.planner.add(new MenuDay("Monday"));
        this.planner.add(new MenuDay("Tuesday"));
        this.planner.add(new MenuDay("Wednesday"));
        this.planner.add(new MenuDay("Thursday"));
        this.planner.add(new MenuDay("Friday"));
        this.planner.add(new MenuDay("Saturday"));
        this.planner.add(new MenuDay("Sunday"));

        for (MenuDay day : this.planner) {
            day.addSampleMeals();
        }

        EventBus.getDefault().postSticky(new MenuMessageEvent(this.planner));
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MenuMessageEvent event) {
        System.out.println("Menu Message Received by service");
    }
}
