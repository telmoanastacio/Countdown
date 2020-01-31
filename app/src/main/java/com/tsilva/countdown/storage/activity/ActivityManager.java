package com.tsilva.countdown.storage.activity;

import android.content.Intent;

import com.tsilva.countdown.modules.loginScreen.activity.LoginScreenActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Telmo Silva on 10.01.2020.
 */

public final class ActivityManager
{
    private static ActivityManager activityManagerInstance = null;

    private CurrentActivity currentActivity = null;
    private List<CurrentActivity> currentActivityList = null;

    private ActivityManager() {}

    public static ActivityManager activityManagerInstance()
    {
        if(activityManagerInstance == null)
        {
            activityManagerInstance = new ActivityManager();
        }
        return activityManagerInstance;
    }

    public CurrentActivity getCurrentActivity()
    {
        return currentActivity;
    }

    public void setCurrentActivity(CurrentActivity currentActivity) throws Throwable
    {
        if(currentActivity == null)
        {
            throw new Throwable("No valid activity provided");
        }

        this.currentActivity = currentActivity;

        if(this.currentActivityList == null)
        {
            this.currentActivityList = new LinkedList<>();
        }

        this.currentActivityList.add(this.currentActivity);
    }

    public void backToLoginScreen()
    {
        CurrentActivity currentActivity = getCurrentActivity();
        Intent loginScreen = new Intent(currentActivity, LoginScreenActivity.class);
        loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        currentActivity.startActivity(loginScreen);

        clearCurrentActivityStack();
    }

    public <T extends CurrentActivity> void changeActivityAndClearCurrent(Class<T> activity)
    {
        CurrentActivity currentActivity = getCurrentActivity();
        Intent changeToActivity = new Intent(currentActivity, activity);
        changeToActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        currentActivity.startActivity(changeToActivity);

        if(currentActivityList != null)
        {
            if(!currentActivityList.isEmpty())
            {
                int index = currentActivityList.size() - 1;
                CurrentActivity currentActivityListItem = currentActivityList.get(index);
                if(currentActivityListItem != null)
                {
                    currentActivityListItem.finishAffinity();
                    currentActivityListItem = null;
                    currentActivityList.remove(index);
                }
            }
        }
    }

    public <T extends CurrentActivity> void changeActivityAndClearSpecificActivities(
            Class<T> changeTo,
            List<Class> clearActivities)
    {
        CurrentActivity currentActivity = getCurrentActivity();
        Intent changeToActivity = new Intent(currentActivity, changeTo);
        changeToActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        currentActivity.startActivity(changeToActivity);

        clearSpecificActivities(clearActivities);
    }

    public <T extends CurrentActivity> void clearSpecificActivities(List<Class> clearActivities)
    {
        if(currentActivityList != null)
        {
            if(!currentActivityList.isEmpty())
            {
                for(int i = currentActivityList.size() - 1; i >= 0; i--)
                {
                    CurrentActivity currentActivityListItem = currentActivityList.get(i);
                    if(clearActivities.contains(currentActivityListItem.getClass()))
                    {
                        currentActivityListItem.finishAffinity();
                        currentActivityListItem = null;
                        currentActivityList.remove(i);
                    }
                }
            }
        }
    }

    public void clearCurrentActivityStack()
    {
        if(currentActivityList != null)
        {
            for(CurrentActivity currentActivity : currentActivityList)
            {
                if(currentActivity != null)
                {
                    currentActivity.finishAffinity();
                }
            }
            currentActivityList.clear();
        }
    }
}