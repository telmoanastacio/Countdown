package com.tsilva.countdown.Api.RestClient;

import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.PasswordResetRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.PasswordResetResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignInRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignInResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUpRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUpResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.VerifyEmailRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.VerifyEmailResponseBodyDto;

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
}