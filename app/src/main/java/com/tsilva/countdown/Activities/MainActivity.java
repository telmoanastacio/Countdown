package com.tsilva.countdown.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.DeleteAccount.DeleteAccountRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.DeleteAccount.DeleteAccountResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.PasswordReset.PasswordResetRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.PasswordReset.PasswordResetResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.PostCountdownEvent.PostCountdownEventRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignIn.SignInRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignIn.SignInResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUp.SignUpRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUp.SignUpResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.VerifyEmail.VerifyEmailRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.VerifyEmail.VerifyEmailResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.PostCountdownEvent.PostCountdownEventResponseBodyDto;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientDeleteAccount;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientEmailVerification;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientPasswordReset;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientSignIn;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientSignUp;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseRealtimeDBApiClientPostCountdownEvent;
import com.tsilva.countdown.Api.RestClient.ResponseCallback;
import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.R;
import com.tsilva.countdown.Services.ImageProcessingService;
import com.tsilva.countdown.Services.PermissionsService;
import com.tsilva.countdown.Services.PersistenceService;

import java.util.ArrayList;

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

    @Inject
    PostFirebaseAuthApiClientSignIn postFirebaseAuthApiClientSignIn;

    @Inject
    PostFirebaseAuthApiClientEmailVerification postFirebaseAuthApiClientEmailVerification;

    @Inject
    PostFirebaseAuthApiClientPasswordReset postFirebaseAuthApiClientPasswordReset;

    @Inject
    PostFirebaseAuthApiClientDeleteAccount postFirebaseAuthApiClientDeleteAccount;

    @Inject
    PostFirebaseRealtimeDBApiClientPostCountdownEvent
            postFirebaseRealtimeDBApiClientPostCountdownEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        CountdownApp.applicationComponent.inject(this);
        setContentView(R.layout.activity_main);
        permissionsService.getPermissions();

        PostCountdownEventRequestBodyDto postCountdownEventRequestBodyDto =
                new PostCountdownEventRequestBodyDto(
                        "tberlinera11@hotmail.com",
                        "Test title 3",
                        "Test details 3",
                        "",
                        new ArrayList<String>(0),
                        123425543L,
                        123425543L);
        fetchPostCountdownEvent(postCountdownEventRequestBodyDto);

//        SignUpRequestBodyDto signUpRequestBodyDto =
//                new SignUpRequestBodyDto("atumfirifiru@atum.com", "12345Thbjnvf");
//        fetchSignUpData(signUpRequestBodyDto);
//        SignInRequestBodyDto signInRequestBodyDto =
//                new SignInRequestBodyDto("atumfirifiru@atum.com", "12345Thbjnvf");
//        fetchSignInData(signInRequestBodyDto);
//        PasswordResetRequestBodyDto passwordResetRequestBodyDto =
//                new PasswordResetRequestBodyDto("atumfirifiru@atum.com");
//        fetchPasswordResetData(passwordResetRequestBodyDto);

        Uri uri = Uri.parse("android.resource://" + MainActivity.getMainContext()
                .getPackageName() + "/" + R.raw.moon);
        persistenceService.loadCachedImages();
        imageProcessingService.constantFrameRateBuildCache(uri, 100L);
        System.out.println();
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

    private void fetchSignUpData(SignUpRequestBodyDto signUpRequestBodyDto)
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

    private void fetchEmailVerificationData(VerifyEmailRequestBodyDto verifyEmailRequestBodyDto)
    {
        if(verifyEmailRequestBodyDto != null)
        {
            postFirebaseAuthApiClientEmailVerification.execute(verifyEmailRequestBodyDto,
            new ResponseCallback<VerifyEmailResponseBodyDto>()
            {
                @Override
                public void success(VerifyEmailResponseBodyDto verifyEmailResponseBodyDto)
                {
                    System.out.println();
                }

                @Override
                public void failure(Throwable t)
                {
                    t.printStackTrace();
                    System.out.println("Couldn't get verification");
                }
            });
        }
    }

    private void fetchSignInData(SignInRequestBodyDto signInRequestBodyDto)
    {
        if(signInRequestBodyDto != null)
        {
            postFirebaseAuthApiClientSignIn.execute(signInRequestBodyDto,
                                                    new ResponseCallback<SignInResponseBodyDto>()
            {
                @Override
                public void success(SignInResponseBodyDto signInResponseBodyDto)
                {
                    fetchEmailVerificationData(
                            new VerifyEmailRequestBodyDto(signInResponseBodyDto.idToken));
                    fetchDeleteAccount(
                            new DeleteAccountRequestBodyDto(signInResponseBodyDto.idToken));
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

    private void fetchPasswordResetData(PasswordResetRequestBodyDto passwordResetRequestBodyDto)
    {
        if(passwordResetRequestBodyDto != null)
        {
            postFirebaseAuthApiClientPasswordReset.execute(passwordResetRequestBodyDto,
                                               new ResponseCallback<PasswordResetResponseBodyDto>()
            {
                @Override
                public void success(PasswordResetResponseBodyDto passwordResetResponseBodyDto)
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
            PostCountdownEventRequestBodyDto postCountdownEventRequestBodyDto)
    {
        if(postCountdownEventRequestBodyDto != null)
        {
            postFirebaseRealtimeDBApiClientPostCountdownEvent.execute(
                    postCountdownEventRequestBodyDto,
                    new ResponseCallback<PostCountdownEventResponseBodyDto>()
            {
                @Override
                public void success(PostCountdownEventResponseBodyDto postCountdownEventResponseBodyDto)
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

    public static Context getMainContext()
    {
        return mainActivity;
    }

    public static MainActivity getMainActivity()
    {
        return mainActivity;
    }
}
