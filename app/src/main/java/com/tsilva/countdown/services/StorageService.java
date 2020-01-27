package com.tsilva.countdown.services;

import android.content.Context;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.persistence.contract.PostsIdToEventMapDto;
import com.tsilva.countdown.storage.activity.ActivityManager;
import com.tsilva.countdown.storage.adapter.AdapterManager;
import com.tsilva.countdown.storage.sharedViewModel.SharedViewModelManager;
import com.tsilva.countdown.storage.status.StatusManager;
import com.tsilva.countdown.storage.utils.UtilsManager;

/**
 * Created by Telmo Silva on 05.12.2019.
 */

public final class StorageService
{
    private static StorageService storageServiceInstance = null;
    private static boolean isInitialized = false;

    private PersistenceService persistenceService = null;
    private ActivityManager activityManager = null;
    private AdapterManager adapterManager = null;
    private StatusManager statusManager = null;
    private SharedViewModelManager sharedViewModelManager = null;
    private UtilsManager utilsManager = null;

    private StorageService() {}

    public static StorageService storageServiceInstance()
    {
        if(storageServiceInstance == null)
        {
            storageServiceInstance = new StorageService();
        }
        return storageServiceInstance;
    }

    public void init(
            PersistenceService persistenceService,
            ActivityManager activityManager,
            AdapterManager adapterManager,
            SharedViewModelManager sharedViewModelManager,
            StatusManager statusManager,
            UtilsManager utilsManager)
    {
        if(!isInitialized)
        {
            this.persistenceService = persistenceService;
            this.activityManager = activityManager;
            this.adapterManager = adapterManager;
            this.sharedViewModelManager = sharedViewModelManager;

            statusManager.init(this.persistenceService, this);
            this.statusManager = statusManager;

            utilsManager.init(this);
            this.utilsManager = utilsManager;
        }

        isInitialized = true;
    }

    public PersistenceService getPersistenceService()
    {
        return persistenceService;
    }

    public ActivityManager getActivityManager()
    {
        return activityManager;
    }

    public AdapterManager getAdapterManager()
    {
        return adapterManager;
    }

    public SharedViewModelManager getSharedViewModelManager()
    {
        return sharedViewModelManager;
    }

    public StatusManager getStatusManager()
    {
        return statusManager;
    }

    public UtilsManager getUtilsManager()
    {
        return utilsManager;
    }

    public Context getContext()
    {
        return CountdownApp.instance;
    }

    public void savePostsIdToEventMapDto(PostsIdToEventMapDto postsIdToEventMapDto)
    {
        persistenceService.saveSerializableObject(
                getContext(),
                postsIdToEventMapDto);

        adapterManager.notifyAdapters(null);
    }

    public PostsIdToEventMapDto loadPostsIdToEventMapDto()
    {
        return persistenceService.loadSerializableObject(
                getContext(),
                PostsIdToEventMapDto.class);
    }
}