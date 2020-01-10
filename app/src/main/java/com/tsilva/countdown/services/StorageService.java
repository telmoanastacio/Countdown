package com.tsilva.countdown.services;

import com.tsilva.countdown.storage.activity.ActivityManager;

/**
 * Created by Telmo Silva on 05.12.2019.
 */

public final class StorageService
{
    private static StorageService storageServiceInstance = null;
    private static boolean isInitialized = false;

    private ActivityManager activityManager = null;

    private StorageService() {}

    public static StorageService storageServiceInstance()
    {
        if(storageServiceInstance == null)
        {
            storageServiceInstance = new StorageService();
        }
        return storageServiceInstance;
    }

    public void init(ActivityManager activityManager)
    {
        if(!isInitialized)
        {
            this.activityManager = activityManager;
        }

        isInitialized = true;
    }

    public ActivityManager getActivityManager()
    {
        return activityManager;
    }
}