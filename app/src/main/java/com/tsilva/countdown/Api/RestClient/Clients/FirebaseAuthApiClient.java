package com.tsilva.countdown.Api.RestClient.Clients;

import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.DeleteAccount.DeleteAccountRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.DeleteAccount.DeleteAccountResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.PasswordReset.PasswordResetRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.PasswordReset.PasswordResetResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignIn.SignInRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignIn.SignInResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUp.SignUpRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUp.SignUpResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.VerifyEmail.VerifyEmailRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.VerifyEmail.VerifyEmailResponseBodyDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Telmo Silva on 06.12.2019.
 */

public interface FirebaseAuthApiClient
{
    @POST("v1/accounts:signUp")
    Call<SignUpResponseBodyDto> postSignUp(
            @Query("key") String API_KEY,
            @Body SignUpRequestBodyDto signUpRequestBodyDto);

    @POST("v1/accounts:sendOobCode")
    Call<VerifyEmailResponseBodyDto> postVerifyEmail(
            @Query("key") String API_KEY,
            @Body VerifyEmailRequestBodyDto verifyEmailRequestBodyDto);

    @POST("v1/accounts:signInWithPassword")
    Call<SignInResponseBodyDto> postSignIn(
            @Query("key") String API_KEY,
            @Body SignInRequestBodyDto signInRequestBodyDto);

    @POST("v1/accounts:sendOobCode")
    Call<PasswordResetResponseBodyDto> postResetPassword(
            @Query("key") String API_KEY,
            @Body PasswordResetRequestBodyDto passwordResetRequestBodyDto);

    @POST("v1/accounts:delete")
    Call<DeleteAccountResponseBodyDto> postDeleteAccount(
            @Query("key") String API_KEY,
            @Body DeleteAccountRequestBodyDto deleteAccountRequestBodyDto);
}