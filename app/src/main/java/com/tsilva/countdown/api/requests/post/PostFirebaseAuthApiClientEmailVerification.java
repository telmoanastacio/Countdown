package com.tsilva.countdown.api.requests.post;

import com.tsilva.countdown.api.contract.firebaseAuthApiClient.verifyEmail.VerifyEmailRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.verifyEmail.VerifyEmailResponseBodyDto;
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

public final class PostFirebaseAuthApiClientEmailVerification
{
    private FirebaseAuthApiClient firebaseAuthApiClient = null;

    public PostFirebaseAuthApiClientEmailVerification(FirebaseAuthApiClient firebaseAuthApiClient)
    {
        this.firebaseAuthApiClient = firebaseAuthApiClient;
    }

    public void execute(VerifyEmailRequestBodyDto verifyEmailRequestBodyDto,
                        final ResponseCallback<VerifyEmailResponseBodyDto> responseCallback)
    {
        firebaseAuthApiClient
                .postVerifyEmail(RestClientConfiguration.FIREBASE_WEB_API_KEY,
                                 verifyEmailRequestBodyDto)
                .enqueue(new Callback<VerifyEmailResponseBodyDto>()
        {
            @Override
            public void onResponse(Call<VerifyEmailResponseBodyDto> call,
                                   Response<VerifyEmailResponseBodyDto> response)
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
            public void onFailure(Call<VerifyEmailResponseBodyDto> call, Throwable t)
            {
                responseCallback.failure(t);
            }
        });
    }
}