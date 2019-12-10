package com.tsilva.countdown.Api.Requests.Post;

import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignIn.SignInRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignIn.SignInResponseBodyDto;
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

public final class PostFirebaseAuthApiClientSignIn
{
    private FirebaseAuthApiClient firebaseAuthApiClient = null;

    public PostFirebaseAuthApiClientSignIn(FirebaseAuthApiClient firebaseAuthApiClient)
    {
        this.firebaseAuthApiClient = firebaseAuthApiClient;
    }

    public void execute(SignInRequestBodyDto signInRequestBodyDto,
                        final ResponseCallback<SignInResponseBodyDto> responseCallback)
    {
        firebaseAuthApiClient
                .postSignIn(RestClientConfiguration.FIREBASE_WEB_API_KEY, signInRequestBodyDto)
                .enqueue(new Callback<SignInResponseBodyDto>()
        {
            @Override
            public void onResponse(Call<SignInResponseBodyDto> call,
                                   Response<SignInResponseBodyDto> response)
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
            public void onFailure(Call<SignInResponseBodyDto> call, Throwable t)
            {
                responseCallback.failure(t);
            }
        });
    }
}