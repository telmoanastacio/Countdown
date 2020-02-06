package com.tsilva.countdown.modules.loginScreen.viewModel;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.tsilva.countdown.R;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.passwordReset.PasswordResetRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.passwordReset.PasswordResetResponseBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.signIn.SignInRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.signIn.SignInResponseBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.signUp.SignUpRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.signUp.SignUpResponseBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.verifyEmail.VerifyEmailRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.verifyEmail.VerifyEmailResponseBodyDto;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientEmailVerification;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientPasswordReset;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientSignIn;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientSignUp;
import com.tsilva.countdown.api.restClient.ResponseCallback;
import com.tsilva.countdown.modules.loginScreen.activity.LoginScreenActivity;
import com.tsilva.countdown.modules.optionsMenu.activity.OptionsMenuActivity;
import com.tsilva.countdown.modules.postList.activity.PostListActivity;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.ImageProcessingService;
import com.tsilva.countdown.services.PermissionsService;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.activity.CurrentActivity;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

enum ViewType
{
    LOADING, CONTENT
}

public final class LoginScreenViewModel
{
    private PermissionsService permissionsService = null;
    private PersistenceService persistenceService = null;
    private ImageProcessingService imageProcessingService = null;
    private StorageService storageService = null;
    private UserLoginCredentials userLoginCredentials = null;
    private PostFirebaseAuthApiClientSignUp postFirebaseAuthApiClientSignUp = null;
    private PostFirebaseAuthApiClientSignIn postFirebaseAuthApiClientSignIn = null;
    private PostFirebaseAuthApiClientEmailVerification postFirebaseAuthApiClientEmailVerification =
            null;
    private PostFirebaseAuthApiClientPasswordReset postFirebaseAuthApiClientPasswordReset = null;
    private Context context = null;

    public LoginScreenObservables loginScreenObservables = null;

    private LoginScreenViewModel() {}

    LoginScreenViewModel(
            PermissionsService permissionsService,
            PersistenceService persistenceService,
            ImageProcessingService imageProcessingService,
            StorageService storageService,
            UserLoginCredentials userLoginCredentials,
            PostFirebaseAuthApiClientSignUp postFirebaseAuthApiClientSignUp,
            PostFirebaseAuthApiClientSignIn postFirebaseAuthApiClientSignIn,
            PostFirebaseAuthApiClientEmailVerification postFirebaseAuthApiClientEmailVerification,
            PostFirebaseAuthApiClientPasswordReset postFirebaseAuthApiClientPasswordReset,
            Context context)
    {
        this.permissionsService = permissionsService;
        this.persistenceService = persistenceService;
        this.imageProcessingService = imageProcessingService;
        this.userLoginCredentials = userLoginCredentials;
        this.storageService = storageService;
        this.postFirebaseAuthApiClientSignUp = postFirebaseAuthApiClientSignUp;
        this.postFirebaseAuthApiClientSignIn = postFirebaseAuthApiClientSignIn;
        this.postFirebaseAuthApiClientEmailVerification =
                postFirebaseAuthApiClientEmailVerification;
        this.postFirebaseAuthApiClientPasswordReset = postFirebaseAuthApiClientPasswordReset;
        this.context = context;

        this.permissionsService.getPermissions();

        setup();
    }

    private void setup()
    {
        this.loginScreenObservables = new LoginScreenObservables();
        showView(ViewType.LOADING);

        // do things here

        showView(ViewType.CONTENT);
    }

    private void showView(ViewType viewType)
    {
        loginScreenObservables.loadingViewVisibility
                .set(viewType == ViewType.LOADING ? View.VISIBLE : View.GONE);
        loginScreenObservables.loginInterfaceVisibility
                .set(viewType == ViewType.CONTENT ? View.VISIBLE : View.GONE);
    }

    public void onMenuClick(View view)
    {
        CurrentActivity currentActivity = storageService.getActivityManager().getCurrentActivity();
        Intent optionMenu = new Intent(currentActivity, OptionsMenuActivity.class);
        optionMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        currentActivity.startActivity(optionMenu);
    }

    public void onSignUpButtonClick(View view)
    {
        if(validateContent())
        {
            fetchSignUpData(new SignUpRequestBodyDto(
                    userLoginCredentials.getEmail(),
                    userLoginCredentials.getPassword()));
        }
    }

    public void onLoginButtonClick(View view)
    {
        if(validateContent())
        {
            fetchSignInData(new SignInRequestBodyDto(
                    userLoginCredentials.getEmail(),
                    userLoginCredentials.getPassword()));
        }
    }

    public void onForgotPasswordButtonClick(View view)
    {
        if(validateEmail())
        {
            fetchPasswordResetData(new PasswordResetRequestBodyDto(
                    userLoginCredentials.getEmail()));
        }
    }

    private boolean validateEmail()
    {
        return this.storageService.getUtilsManager().validateEmail(
                this.loginScreenObservables.emailTextContent.get());
    }

    private boolean validatePassword()
    {
        String str = this.loginScreenObservables.passwordTextContent.get();
        if(str != null)
        {
            str = str.trim();

            return str.length() >= 6;
        }

        return false;
    }

    private boolean validateContent()
    {
        boolean isValidEmail = validateEmail();
        boolean isValidPassword = validatePassword();

        if(!isValidEmail && !isValidPassword)
        {
            this.loginScreenObservables.alertTextContent.set(
                    context.getString(R.string.invalidCredentials));
        }
        else if(!isValidEmail)
        {
            this.loginScreenObservables.alertTextContent.set(
                    context.getString(R.string.invalidEmail));
        }
        else if(!isValidPassword)
        {
            this.loginScreenObservables.alertTextContent.set(
                    context.getString(R.string.invalidPassword));
        }
        else
        {
            this.loginScreenObservables.alertTextContent.set("");
        }

        if(isValidEmail && isValidPassword)
        {
            String email = this.loginScreenObservables.emailTextContent.get().trim();
            String password = this.loginScreenObservables.passwordTextContent.get().trim();
            userLoginCredentials.setLastUsedCredentials(email, password);
        }

        return isValidEmail && isValidPassword;
    }

    private void fetchSignUpData(SignUpRequestBodyDto signUpRequestBodyDto)
    {
        if(signUpRequestBodyDto != null)
        {
            postFirebaseAuthApiClientSignUp.execute(
                    signUpRequestBodyDto,
                    new ResponseCallback<SignUpResponseBodyDto>()
                    {
                        @Override
                        public void success(SignUpResponseBodyDto signUpResponseBodyDto)
                        {
                            String idToken = signUpResponseBodyDto.idToken;
                            fetchEmailVerificationData(
                                    new VerifyEmailRequestBodyDto(idToken));
                            userLoginCredentials.setIdToken(idToken);
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            t.printStackTrace();
                            if(LoginScreenActivity.isAlive)
                            {
                                loginScreenObservables.alertTextContent.set(
                                        context.getString(R.string.signUpFailed));
                            }
                        }
                    });
        }
    }

    private void fetchEmailVerificationData(VerifyEmailRequestBodyDto verifyEmailRequestBodyDto)
    {
        if(verifyEmailRequestBodyDto != null)
        {
            postFirebaseAuthApiClientEmailVerification.execute(
                    verifyEmailRequestBodyDto,
                    new ResponseCallback<VerifyEmailResponseBodyDto>()
                    {
                        @Override
                        public void success(VerifyEmailResponseBodyDto verifyEmailResponseBodyDto)
                        {
                            if(LoginScreenActivity.isAlive)
                            {
                                loginScreenObservables.alertTextContent.set(
                                        context.getString(R.string.emailVerificationSent));
                            }
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            t.printStackTrace();
                            if(LoginScreenActivity.isAlive)
                            {
                                loginScreenObservables.alertTextContent.set(
                                        context.getString(R.string.emailVerificationFailed));
                            }
                        }
                    });
        }
    }

    private void fetchSignInData(SignInRequestBodyDto signInRequestBodyDto)
    {
        if(signInRequestBodyDto != null)
        {
            postFirebaseAuthApiClientSignIn.execute(
                    signInRequestBodyDto,
                    new ResponseCallback<SignInResponseBodyDto>()
                    {
                        @Override
                        public void success(SignInResponseBodyDto signInResponseBodyDto)
                        {
                            if(signInResponseBodyDto.registered)
                            {
                                if(LoginScreenActivity.isAlive)
                                {
                                    loginScreenObservables.alertTextContent.set("");
                                }

                                userLoginCredentials.setIdToken(signInResponseBodyDto.idToken);

                                Uri uri = Uri.parse("android.resource://" + context
                                        .getPackageName() + "/" + R.raw.moon);
                                if(persistenceService.loadCachedImages() == null)
                                {
                                    imageProcessingService.constantFrameRateBuildCache(
                                            uri, 100L);
                                }

                                // go to next activity
                                CurrentActivity currentActivity =
                                        storageService.getActivityManager().getCurrentActivity();
                                Intent postList =
                                        new Intent(currentActivity, PostListActivity.class);
                                postList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                          | Intent.FLAG_ACTIVITY_NEW_TASK);

                                currentActivity.startActivity(postList);

                                storageService.getActivityManager().clearCurrentActivityStack();
                            }
                            else
                            {
                                if(LoginScreenActivity.isAlive)
                                {
                                    loginScreenObservables.alertTextContent.set(
                                            context.getString(R.string.registerFirst));
                                }
                            }
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            t.printStackTrace();
                            if(LoginScreenActivity.isAlive)
                            {
                                loginScreenObservables.alertTextContent.set(
                                        context.getString(R.string.loginFailed));
                            }
                        }
                    });
        }
    }

    private void fetchPasswordResetData(PasswordResetRequestBodyDto passwordResetRequestBodyDto)
    {
        if(passwordResetRequestBodyDto != null)
        {
            postFirebaseAuthApiClientPasswordReset.execute(
                    passwordResetRequestBodyDto,
                    new ResponseCallback<PasswordResetResponseBodyDto>()
                    {
                        @Override
                        public void success(PasswordResetResponseBodyDto passwordResetResponseBodyDto)
                        {
                            if(LoginScreenActivity.isAlive)
                            {
                                loginScreenObservables.alertTextContent.set(
                                        context.getString(R.string.passwordResetSent));
                            }
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            t.printStackTrace();
                            if(LoginScreenActivity.isAlive)
                            {
                                loginScreenObservables.alertTextContent.set(
                                        context.getString(R.string.passwordResetFailed));
                            }
                        }
                    });
        }
    }

    public final class LoginScreenObservables
    {
        public ObservableInt loadingViewVisibility = new ObservableInt();
        public ObservableInt loginInterfaceVisibility = new ObservableInt();

        public ObservableField<String> emailTextContent = new ObservableField<>();
        public ObservableField<String> passwordTextContent = new ObservableField<>();
        public ObservableField<String> alertTextContent = new ObservableField<>();

        public ObservableField<Drawable> imageViewDrawable = new ObservableField<>();

        private LoginScreenObservables()
        {
            loadingViewVisibility.set(View.VISIBLE);
            loginInterfaceVisibility.set(View.GONE);

            if(userLoginCredentials.hasLastStoredCredentials())
            {
                emailTextContent.set(userLoginCredentials.getEmail());
            }
            else
            {
                emailTextContent.set("");
            }
            passwordTextContent.set("");
            alertTextContent.set("");

            imageViewDrawable.set(null);
        }
    }
}