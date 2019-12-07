package com.tsilva.countdown.Api.RestClient;

import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUpRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUpResponseBodyDto;

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
}