package com.tsilva.countdown.storage.status;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.tsilva.countdown.R;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;

import java.util.Date;

/**
 * Created by Telmo Silva on 24.01.2020.
 */

public final class StatusManager
{
    private static volatile StatusManager statusManagerInstance = null;
    private static boolean isInitialized = false;

    private PersistenceService persistenceService = null;
    private StorageService storageService = null;

    private StatusManager() {}

    public static StatusManager statusManagerInstance()
    {
        if(statusManagerInstance == null)
        {
            synchronized(StatusManager.class)
            {
                if(statusManagerInstance == null)
                {
                    statusManagerInstance = new StatusManager();
                }
            }
        }
        return statusManagerInstance;
    }

    public void init(
            PersistenceService persistenceService,
            StorageService storageService)
    {
        if(!isInitialized)
        {
            this.persistenceService = persistenceService;
            this.storageService = storageService;
        }

        isInitialized = true;
    }

    /**
     *
     * @return remaining time factor between 0.0 and 1.0
     */
    private double getRemainingTimeFactor(CountdownEventDto countdownEventDto, Date now)
    {
        if(countdownEventDto == null)
        {
            return 0.0;
        }

        Long tsi = countdownEventDto.tsi;
        Long tsf = countdownEventDto.tsf;

        if(tsi == null || tsf == null || tsf <= 0)
        {
            return 0.0;
        }

        long totalTime = 0L;
        totalTime = tsf - tsi;

        if(totalTime < 0L)
        {
            totalTime = 0L;
        }

        double remainingTimeFactor = Long.valueOf(tsf - now.getTime()).doubleValue()
                / Long.valueOf(totalTime).doubleValue();

        if(remainingTimeFactor < 0.0)
        {
            remainingTimeFactor = 0.0;
        }
        else if(remainingTimeFactor > 1.0)
        {
            remainingTimeFactor = 1.0;
        }

        return remainingTimeFactor;
    }

    public double getProgress(CountdownEventDto countdownEventDto, Date now)
    {
//        // for development
//        double progress = 1.0 - getRemainingTimeFactor(countdownEventDto, now);
//        return progress;

        // for production
        return 1.0 - getRemainingTimeFactor(countdownEventDto, now);
    }

    public int getProgressColor(CountdownEventDto countdownEventDto, Date now)
    {
        double progress = getProgress(countdownEventDto, now);

        if(progress >= 0.9)
        {
            return ContextCompat.getColor(storageService.getContext(), R.color.red);
        }
        else if(progress >= 0.7)
        {
            return ContextCompat.getColor(storageService.getContext(), R.color.orange);
        }
        else
        {
            return ContextCompat.getColor(storageService.getContext(), R.color.green);
        }
    }

    public Drawable getDrawable(CountdownEventDto countdownEventDto, Date now)
    {
        Drawable[] imagesCache = persistenceService.loadCachedImages();
        int cacheSize = 0;

        if(imagesCache == null || imagesCache.length == 0)
        {
            return null;
        }
        cacheSize = imagesCache.length;

        int position =
                Double.valueOf(((getProgress(countdownEventDto, now))
                        * Integer.valueOf(cacheSize).doubleValue())).intValue();

        if(position > 0 && position == cacheSize)
        {
            position = cacheSize - 1;
        }

        return imagesCache[position];
    }
}