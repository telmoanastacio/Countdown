package com.tsilva.countdown.modules.loginScreen.viewModel;

import android.content.Context;
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
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.ImageProcessingService;
import com.tsilva.countdown.services.PermissionsService;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

enum ViewType
{
    LOADING, CONTENT
}

public final class LoginScreenViewModel
{
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile(
                    "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);

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

        setupObservables();
    }

    private void setupObservables()
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
        String str = this.loginScreenObservables.emailTextContent.get();
        if(str != null)
        {
            str = str.trim();

            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(str);

            return matcher.find();
        }

        return false;
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
                            fetchEmailVerificationData(
                                    new VerifyEmailRequestBodyDto(signUpResponseBodyDto.idToken));
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            t.printStackTrace();
                            loginScreenObservables.alertTextContent.set(
                                    context.getString(R.string.signUpFailed));
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
                            loginScreenObservables.alertTextContent.set(
                                    context.getString(R.string.emailVerificationSent));
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            t.printStackTrace();
                            loginScreenObservables.alertTextContent.set(
                                    context.getString(R.string.emailVerificationFailed));
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
                                loginScreenObservables.alertTextContent.set("");

                                Uri uri = Uri.parse("android.resource://" + context
                                        .getPackageName() + "/" + R.raw.moon);
                                Drawable[] images = persistenceService.loadCachedImages();
                                if(images == null)
                                {
                                    imageProcessingService.constantFrameRateBuildCache(uri, 100L);
                                    images = persistenceService.loadCachedImages();
                                }
                                // go to next activity
                            }
                            else
                            {
                                loginScreenObservables.alertTextContent.set(
                                        context.getString(R.string.registerFirst));
                            }
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            t.printStackTrace();
                            loginScreenObservables.alertTextContent.set(
                                    context.getString(R.string.loginFailed));
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
                            loginScreenObservables.alertTextContent.set(
                                    context.getString(R.string.passwordResetSent));
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            t.printStackTrace();
                            loginScreenObservables.alertTextContent.set(
                                    context.getString(R.string.passwordResetFailed));
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

            emailTextContent.set(userLoginCredentials.getEmail());
            passwordTextContent.set("");
            alertTextContent.set("");

            imageViewDrawable.set(null);
        }
    }
}