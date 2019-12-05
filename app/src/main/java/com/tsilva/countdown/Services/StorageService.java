package com.tsilva.countdown.Services;

/**
 * Created by Telmo Silva on 05.12.2019.
 */

public final class StorageService
{
    private static StorageService storageServiceInstance;

    private StorageService() {}

    public static StorageService storageServiceInstance()
    {
        if(storageServiceInstance == null)
        {
            storageServiceInstance = new StorageService();
        }
        return storageServiceInstance;
    }
}