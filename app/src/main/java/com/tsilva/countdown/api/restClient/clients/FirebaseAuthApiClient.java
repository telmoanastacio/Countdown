package com.tsilva.countdown.api.restClient.clients;

import com.tsilva.countdown.api.contract.firebaseAuthApiClient.deleteAccount.DeleteAccountRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.deleteAccount.DeleteAccountResponseBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.passwordReset.PasswordResetRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.passwordReset.PasswordResetResponseBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.signIn.SignInRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.signIn.SignInResponseBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.signUp.SignUpRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.signUp.SignUpResponseBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.verifyEmail.VerifyEmailRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.verifyEmail.VerifyEmailResponseBodyDto;

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