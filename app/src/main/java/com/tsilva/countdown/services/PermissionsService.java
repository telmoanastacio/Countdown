package com.tsilva.countdown.services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

public final class PermissionsService
{
    public static final int READ_EXTERNAL_STORAGE_CODE = 9010;
    public static final int WRITE_EXTERNAL_STORAGE_CODE = 9011;

    private Context context = null;
    private StorageService storageService = null;

    private PermissionsService() {}

    private PermissionsService(Context context, StorageService storageService)
    {
        this.context = context;
        this.storageService = storageService;
    }

    public static PermissionsService permissionsServiceInstance(
            Context context,
            StorageService storageService)
    {
        return new PermissionsService(context, storageService);
    }

    public void getPermissions()
    {
        getReadExternalStoragePermission();
        getWriteExternalStoragePermission();
    }

    private void getReadExternalStoragePermission()
    {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
        {
            int permissionCheck  = 0;
            permissionCheck = ContextCompat
                    .checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

            if(permissionCheck != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat
                        .requestPermissions(storageService.getCurrentActivity(),
                                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                            READ_EXTERNAL_STORAGE_CODE);
            }
        }
    }

    private void getWriteExternalStoragePermission()
    {
        int permissionCheck  = ContextCompat
                .checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat
                    .requestPermissions(storageService.getCurrentActivity(),
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        WRITE_EXTERNAL_STORAGE_CODE);
        }
    }
}