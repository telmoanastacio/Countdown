package com.tsilva.countdown.storage.activity;

import java.util.Stack;

/**
 * Created by Telmo Silva on 10.01.2020.
 */

public final class ActivityManager
{
    private static ActivityManager activityManagerInstance = null;

    private CurrentActivity currentActivity = null;
    private Stack<CurrentActivity> currentActivityStack = null;

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

        if(this.currentActivityStack == null)
        {
            this.currentActivityStack = new Stack<>();
        }

        this.currentActivityStack.push(this.currentActivity);
    }

    public void clearCurrentActivityStack()
    {
        if(currentActivityStack != null)
        {
            while(!currentActivityStack.isEmpty())
            {
                CurrentActivity currentActivity = currentActivityStack.pop();
                if(currentActivity != null)
                {
                    currentActivity.finishAffinity();
                }
            }
        }
    }
}