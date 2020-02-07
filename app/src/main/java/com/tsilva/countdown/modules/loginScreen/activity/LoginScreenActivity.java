package com.tsilva.countdown.modules.loginScreen.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.tsilva.countdown.BuildConfig;
import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.R;
import com.tsilva.countdown.api.restClient.RestClientConfiguration;
import com.tsilva.countdown.databinding.LoginScreenActivityBinding;
import com.tsilva.countdown.modules.loginScreen.viewModel.LoginScreenViewModelFactory;
import com.tsilva.countdown.services.PermissionsService;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.activity.CurrentActivity;

import javax.inject.Inject;

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

        getPrivateKeys();

        loginScreenActivityBinding = DataBindingUtil
                .setContentView(this, R.layout.login_screen_activity);
        loginScreenActivityBinding.setViewModel(loginScreenViewModelFactory.create());

        loginScreenActivityBinding.executePendingBindings();
    }

    @Override
    protected void onDestroy()
    {
        isAlive = false;
        storageService.getActivityManager().clearActivityByObject(this);
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handleRequestPermissionsResult(requestCode, permissions, grantResults);
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

    /**
     * If the app is generated containing a private key.
     * It will work with a private database service.
     */
    private void getPrivateKeys()
    {
        String privateKey = persistenceService
                .loadSerializableObject(context, String.class, "privateKey");
        if(privateKey == null || privateKey.isEmpty())
        {
            // empty the filed after generating file with the private key
            privateKey = "";

            if(!privateKey.isEmpty())
            {
                persistenceService.saveSerializableObject(context, privateKey, "privateKey");
            }
        }

        if(privateKey != null && !privateKey.isEmpty())
        {
            StringBuilder decodedKeySB = new StringBuilder();
            decodedKeySB.append("");

            String[] chars = privateKey.split("\\.");

            for(String intChar : chars)
            {
                int character = Integer.valueOf(intChar);
                decodedKeySB.append((char) character);
            }

            String[] keys = decodedKeySB.toString().split(";");

            RestClientConfiguration.FIREBASE_WEB_API_KEY = keys[0];
            RestClientConfiguration.FIREBASE_REALTIME_DB_API_KEY = keys[1];
            RestClientConfiguration.FIREBASE_REALTIME_DB_API_CLIENT_ENDPOINT = keys[2];
        }

        // This extra is passed trough launch by commands
        // generated apk can be found in ./app/build/outputs/apk/debug
        // adb install -t app/build/outputs/apk/debug/app-debug.apk
        String message = getIntent().getStringExtra("Message");
        if(message != null)
        {
            String[] args = message.split(";");
            RestClientConfiguration.FIREBASE_WEB_API_KEY = args[0];
            RestClientConfiguration.FIREBASE_REALTIME_DB_API_KEY = args[1];
            RestClientConfiguration.FIREBASE_REALTIME_DB_API_CLIENT_ENDPOINT = args[2];
            System.out.println(
                    "=== generated apk can be found in ./app/build/outputs/apk/debug ===");
        }
    }
}
