package com.tsilva.countdown.modules.loginScreen.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.R;
import com.tsilva.countdown.api.requests.delete.DeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent;
import com.tsilva.countdown.api.restClient.ResponseCallback;
import com.tsilva.countdown.databinding.LoginScreenActivityBinding;
import com.tsilva.countdown.modules.loginScreen.viewModel.LoginScreenViewModelFactory;
import com.tsilva.countdown.services.ImageProcessingService;
import com.tsilva.countdown.services.PermissionsService;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.activity.CurrentActivity;

import javax.inject.Inject;

import okhttp3.ResponseBody;

public final class LoginScreenActivity extends CurrentActivity
{
    public static boolean isAlive = false;

    @Inject
    Context context;

    @Inject
    PermissionsService permissionsService;

    @Inject
    PersistenceService persistenceService;

    @Inject
    StorageService storageService;

    @Inject
    LoginScreenViewModelFactory loginScreenViewModelFactory;

    @Inject
    ImageProcessingService imageProcessingService;

    @Inject
    DeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent
            deleteFirebaseRealtimeDBApiClientUpdateCountdownEvent;

    private LoginScreenActivityBinding loginScreenActivityBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isAlive = true;

//        //Remove title bar
//        getSupportActionBar().hide();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //Remove notification bar
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        CountdownApp.applicationComponent.inject(this);
        setCurrentActivity();

        loginScreenActivityBinding = DataBindingUtil
                .setContentView(this, R.layout.login_screen_activity);
        loginScreenActivityBinding.setViewModel(loginScreenViewModelFactory.create());

        loginScreenActivityBinding.executePendingBindings();
    }

    @Override
    protected void onDestroy()
    {
        isAlive = false;
        super.onDestroy();
    }

    @Override
    public CurrentActivity getCurrentActivity()
    {
        return storageService.getActivityManager().getCurrentActivity();
    }

    @Override
    public void setCurrentActivity()
    {
        try
        {
            storageService.getActivityManager().setCurrentActivity(this);
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        handleRequestPermissionsResult(requestCode, permissions, grantResults);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void handleRequestPermissionsResult(int requestCode,
                                                @NonNull String[] permissions,
                                                @NonNull int[] grantResults)
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
    }

    private void fetchDeleteCountdownEvent(String postId)
    {
        if(postId != null)
        {
            deleteFirebaseRealtimeDBApiClientUpdateCountdownEvent
                    .execute(postId,
                             new ResponseCallback<ResponseBody>()
            {
                @Override
                public void success(ResponseBody responseBody)
                {
                    System.out.println();
                }

                @Override
                public void failure(Throwable t)
                {
                    t.printStackTrace();
                    System.out.println("Couldn't sign in");
                }
            });
        }
    }
}
