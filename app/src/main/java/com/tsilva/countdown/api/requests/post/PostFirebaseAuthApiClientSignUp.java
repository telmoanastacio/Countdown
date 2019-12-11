package com.tsilva.countdown.api.requests.post;

import com.tsilva.countdown.api.contract.firebaseAuthApiClient.signUp.SignUpRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.signUp.SignUpResponseBodyDto;
import com.tsilva.countdown.api.requests.ResponseUtils;
import com.tsilva.countdown.api.restClient.ResponseCallback;
import com.tsilva.countdown.api.restClient.RestClientConfiguration;
import com.tsilva.countdown.api.restClient.clients.FirebaseAuthApiClient;

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