package com.tsilva.countdown.modules.loginScreen.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.R;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.deleteAccount.DeleteAccountRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.deleteAccount.DeleteAccountResponseBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventsDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.postCountdownEvent.PostCountdownEventRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.postCountdownEvent.PostCountdownEventResponseBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.updateCountdownEvent.UpdateCountdownEventRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.updateCountdownEvent.UpdateCountdownEventResponseBodyDto;
import com.tsilva.countdown.api.requests.delete.DeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent;
import com.tsilva.countdown.api.requests.get.GetFirebaseRealtimeDBApiClientGetCountdownEvents;
import com.tsilva.countdown.api.requests.patch.PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientDeleteAccount;
import com.tsilva.countdown.api.requests.post.PostFirebaseRealtimeDBApiClientPostCountdownEvent;
import com.tsilva.countdown.api.restClient.ResponseCallback;
import com.tsilva.countdown.databinding.LoginScreenActivityBinding;
import com.tsilva.countdown.modules.loginScreen.viewModel.LoginScreenViewModelFactory;
import com.tsilva.countdown.services.ImageProcessingService;
import com.tsilva.countdown.services.PermissionsService;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.CurrentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;

public final class LoginScreenActivity extends CurrentActivity
{
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
    PostFirebaseAuthApiClientDeleteAccount postFirebaseAuthApiClientDeleteAccount;

    @Inject
    PostFirebaseRealtimeDBApiClientPostCountdownEvent
            postFirebaseRealtimeDBApiClientPostCountdownEvent;

    @Inject
    PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent
            patchFirebaseRealtimeDBApiClientUpdateCountdownEvent;

    @Inject
    DeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent
            deleteFirebaseRealtimeDBApiClientUpdateCountdownEvent;

    @Inject
    GetFirebaseRealtimeDBApiClientGetCountdownEvents
            getFirebaseRealtimeDBApiClientGetCountdownEvents;

    private LoginScreenActivityBinding loginScreenActivityBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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

//        fetchCountdownEvents();

//        PostCountdownEventRequestBodyDto postCountdownEventRequestBodyDto =
//                new PostCountdownEventRequestBodyDto(
//                        "tberlinera11@hotmail.com",
//                        "Test title 3",
//                        "Test details 3",
//                        "",
//                        new ArrayList<String>(0),
//                        123425543L,
//                        123425543L);
//        fetchPostCountdownEvent(postCountdownEventRequestBodyDto);
    }

    @Override
    public CurrentActivity getCurrentActivity()
    {
        return storageService.getCurrentActivity();
    }

    @Override
    public void setCurrentActivity()
    {
        try
        {
            storageService.setCurrentActivity(this);
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

    private void fetchDeleteAccount(DeleteAccountRequestBodyDto deleteAccountRequestBodyDto)
    {
        if(deleteAccountRequestBodyDto != null)
        {
            postFirebaseAuthApiClientDeleteAccount.execute(deleteAccountRequestBodyDto,
                                                           new ResponseCallback<DeleteAccountResponseBodyDto>()
            {
                @Override
                public void success(DeleteAccountResponseBodyDto deleteAccountResponseBodyDto)
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

    private void fetchPostCountdownEvent(
            final PostCountdownEventRequestBodyDto postCountdownEventRequestBodyDto)
    {
        if(postCountdownEventRequestBodyDto != null)
        {
            postFirebaseRealtimeDBApiClientPostCountdownEvent.execute(
                    postCountdownEventRequestBodyDto,
                    new ResponseCallback<PostCountdownEventResponseBodyDto>()
            {
                @Override
                public void success(PostCountdownEventResponseBodyDto
                                            postCountdownEventResponseBodyDto)
                {
                    UpdateCountdownEventRequestBodyDto updateCountdownEventRequestBodyDto =
                            new UpdateCountdownEventRequestBodyDto(
                                    postCountdownEventRequestBodyDto.email,
                                    "Modified title",
                                    postCountdownEventRequestBodyDto.details,
                                    postCountdownEventRequestBodyDto.img,
                                    postCountdownEventRequestBodyDto.shareWith,
                                    postCountdownEventRequestBodyDto.tsi,
                                    postCountdownEventRequestBodyDto.tsf
                            );
                    fetchPatchCountdownEvent(postCountdownEventResponseBodyDto.name,
                                             updateCountdownEventRequestBodyDto);
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

    private void fetchPatchCountdownEvent(
            final String postId,
            UpdateCountdownEventRequestBodyDto updateCountdownEventRequestBodyDto)
    {
        if(postId != null && updateCountdownEventRequestBodyDto != null)
        {
            patchFirebaseRealtimeDBApiClientUpdateCountdownEvent.execute(
                    postId,
                    updateCountdownEventRequestBodyDto,
                    new ResponseCallback<UpdateCountdownEventResponseBodyDto>()
            {
                @Override
                public void success(UpdateCountdownEventResponseBodyDto
                                            updateCountdownEventResponseBodyDto)
                {
                    fetchDeleteCountdownEvent(postId);
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

    private void fetchCountdownEvents()
    {
        getFirebaseRealtimeDBApiClientGetCountdownEvents
                .execute(new ResponseCallback<ResponseBody>()
        {
            @Override
            public void success(ResponseBody responseBody)
            {
                try
                {
                    CountdownEventsDto countdownEventsDto =
                            new CountdownEventsDto(new JSONObject(responseBody.string()));
                    System.out.println();
                }
                catch(JSONException | IOException e)
                {
                    e.printStackTrace();
                }
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
