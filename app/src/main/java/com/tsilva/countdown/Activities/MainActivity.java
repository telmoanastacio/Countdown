package com.tsilva.countdown.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.R;
import com.tsilva.countdown.Services.ImageProcessingService;
import com.tsilva.countdown.Services.PermissionsService;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
{
    private static MainActivity mainActivity = null;

    @Inject
    PermissionsService permissionsService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        CountdownApp.applicationComponent.inject(this);
        setContentView(R.layout.activity_main);
        permissionsService.getPermissions();

        Uri uri = Uri.parse("android.resource://" + MainActivity.getMainContext()
                .getPackageName() + "/" + R.raw.moon);
        List<Bitmap> bitmapList = new ImageProcessingService().constantFrameRateBuildList(uri, 100L);
        System.out.println();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == PermissionsService.READ_EXTERNAL_STORAGE_CODE
                || requestCode == PermissionsService.WRITE_EXTERNAL_STORAGE_CODE)
        {
            for(int result : grantResults)
            {
                if(result == PackageManager.PERMISSION_DENIED)
                {
                    permissionsService.getPermissions();
                    return;
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public static Context getMainContext()
    {
        return mainActivity;
    }

    public static MainActivity getMainActivity()
    {
        return mainActivity;
    }
}
