package com.tsilva.countdown.Api.Requests.Post;

import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignInRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignInResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.VerifyEmailRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.VerifyEmailResponseBodyDto;
import com.tsilva.countdown.Api.Requests.ResponseUtils;
import com.tsilva.countdown.Api.RestClient.FirebaseAuthApiClient;
import com.tsilva.countdown.Api.RestClient.ResponseCallback;
import com.tsilva.countdown.Api.RestClient.RestClientConfiguration;

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