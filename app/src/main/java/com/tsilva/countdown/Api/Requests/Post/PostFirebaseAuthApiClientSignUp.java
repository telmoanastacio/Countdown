package com.tsilva.countdown.Api.Requests.Post;

import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUp.SignUpRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUp.SignUpResponseBodyDto;
import com.tsilva.countdown.Api.Requests.ResponseUtils;
import com.tsilva.countdown.Api.RestClient.Clients.FirebaseAuthApiClient;
import com.tsilva.countdown.Api.RestClient.ResponseCallback;
import com.tsilva.countdown.Api.RestClient.RestClientConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Telmo Silva on 07.12.2019.
 */

public final class PostFirebaseAuthApiClientSignUp
{
    private FirebaseAuthApiClient firebaseAuthApiClient = null;

    public PostFirebaseAuthApiClientSignUp(FirebaseAuthApiClient firebaseAuthApiClient)
    {
        this.firebaseAuthApiClient = firebaseAuthApiClient;
    }

    public void execute(SignUpRequestBodyDto signUpRequestBodyDto,
                        final ResponseCallback<SignUpResponseBodyDto> responseCallback)
    {
        firebaseAuthApiClient
                .postSignUp(RestClientConfiguration.FIREBASE_WEB_API_KEY, signUpRequestBodyDto)
                .enqueue(new Callback<SignUpResponseBodyDto>()
        {
            @Override
            public void onResponse(Call<SignUpResponseBodyDto> call,
                                   Response<SignUpResponseBodyDto> response)
            {
                if(ResponseUtils.isSuccess(response))
                {
                    responseCallback.success(response.body());
                }
                else
                {
                    responseCallback.failure(ResponseUtils.buildStatusCodeThrowable(response));
                }
            }

            @Override
            public void onFailure(Call<SignUpResponseBodyDto> call, Throwable t)
            {
                responseCallback.failure(t);
            }
        });
    }
}