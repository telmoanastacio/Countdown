package com.tsilva.countdown.Api.Requests.Post;

import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.PasswordReset.PasswordResetRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.PasswordReset.PasswordResetResponseBodyDto;
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