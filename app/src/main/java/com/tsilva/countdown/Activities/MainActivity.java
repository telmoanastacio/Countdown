package com.tsilva.countdown.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUpRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUpResponseBodyDto;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientSignUp;
import com.tsilva.countdown.Api.RestClient.ResponseCallback;
import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.R;
import com.tsilva.countdown.Services.ImageProcessingService;
import com.tsilva.countdown.Services.PermissionsService;
import com.tsilva.countdown.Services.PersistenceService;

import javax.inject.Inject;

public final class MainActivity extends AppCompatActivity
{
    private static MainActivity mainActivity = null;

    @Inject
    Context context;

    @Inject
    PermissionsService permissionsService;

    @Inject
    PersistenceService persistenceService;

    @Inject
    ImageProcessingService imageProcessingService;

    @Inject
    PostFirebaseAuthApiClientSignUp postFirebaseAuthApiClientSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        CountdownApp.applicationComponent.inject(this);
        setContentView(R.layout.activity_main);
        permissionsService.getPermissions();

        SignUpRequestBodyDto signUpRequestBodyDto =
                new SignUpRequestBodyDto("atumfirifiri@atum.com", "12345Thbjnvf");
        fetchData(signUpRequestBodyDto);

        Uri uri = Uri.parse("android.resource://" + MainActivity.getMainContext()
                .getPackageName() + "/" + R.raw.moon);
        persistenceService.loadCachedImages();
        imageProcessingService.constantFrameRateBuildCache(uri, 100L);
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

    private void fetchData(SignUpRequestBodyDto signUpRequestBodyDto)
    {
        if(signUpRequestBodyDto != null)
        {
            postFirebaseAuthApiClientSignUp.execute(signUpRequestBodyDto,
                                                    new ResponseCallback<SignUpResponseBodyDto>()
            {
                @Override
                public void success(SignUpResponseBodyDto signUpResponseBodyDto)
                {
                    System.out.println();
                }

                @Override
                public void failure(Throwable t)
                {
                    t.printStackTrace();
                    System.out.println("Couldn't sign up");
                }
            });
        }
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
