package com.tsilva.countdown.api.requests.post;

import com.tsilva.countdown.api.contract.firebaseAuthApiClient.passwordReset.PasswordResetRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.passwordReset.PasswordResetResponseBodyDto;
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

public final class PostFirebaseAuthApiClientPasswordReset
{
    private FirebaseAuthApiClient firebaseAuthApiClient = null;

    public PostFirebaseAuthApiClientPasswordReset(FirebaseAuthApiClient firebaseAuthApiClient)
    {
        this.firebaseAuthApiClient = firebaseAuthApiClient;
    }

    public void execute(PasswordResetRequestBodyDto passwordResetRequestBodyDto,
                        final ResponseCallback<PasswordResetResponseBodyDto> responseCallback)
    {
        firebaseAuthApiClient
                .postResetPassword(RestClientConfiguration.FIREBASE_WEB_API_KEY,
                                 passwordResetRequestBodyDto)
                .enqueue(new Callback<PasswordResetResponseBodyDto>()
        {
            @Override
            public void onResponse(Call<PasswordResetResponseBodyDto> call,
                                   Response<PasswordResetResponseBodyDto> response)
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
            public void onFailure(Call<PasswordResetResponseBodyDto> call, Throwable t)
            {
                responseCallback.failure(t);
            }
        });
    }
}