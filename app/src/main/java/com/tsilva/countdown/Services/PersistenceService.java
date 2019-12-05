package com.tsilva.countdown.Services;

import android.content.Context;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

public final class PersistenceService
{
    private Context context = null;

    private PersistenceService() {}

    private PersistenceService(Context context)
    {
        this.context = context;
    }

    public static PersistenceService persistenceServiceInstance(Context context)
    {
        return new PersistenceService(context);
    }
}