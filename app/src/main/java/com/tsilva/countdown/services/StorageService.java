package com.tsilva.countdown.services;

import com.tsilva.countdown.storage.CurrentActivity;

/**
 * Created by Telmo Silva on 05.12.2019.
 */

public final class StorageService
{
    private static StorageService storageServiceInstance;

    private CurrentActivity currentActivity = null;

    private StorageService() {}

    public static StorageService storageServiceInstance()
    {
        if(storageServiceInstance == null)
        {
            storageServiceInstance = new StorageService();
        }
        return storageServiceInstance;
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
    }
}