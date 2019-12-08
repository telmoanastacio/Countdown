package com.tsilva.countdown.Api.Requests.Post;

import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.DeleteAccountRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.DeleteAccountResponseBodyDto;
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

public final class PostFirebaseAuthApiClientDeleteAccount
{
    private FirebaseAuthApiClient firebaseAuthApiClient = null;

    public PostFirebaseAuthApiClientDeleteAccount(FirebaseAuthApiClient firebaseAuthApiClient)
    {
        this.firebaseAuthApiClient = firebaseAuthApiClient;
    }

    public void execute(DeleteAccountRequestBodyDto deleteAccountRequestBodyDto,
                        final ResponseCallback<DeleteAccountResponseBodyDto> responseCallback)
    {
        firebaseAuthApiClient
                .postDeleteAccount(RestClientConfiguration.FIREBASE_WEB_API_KEY,
                                 deleteAccountRequestBodyDto)
                .enqueue(new Callback<DeleteAccountResponseBodyDto>()
        {
            @Override
            public void onResponse(Call<DeleteAccountResponseBodyDto> call, Response<DeleteAccountResponseBodyDto> response)
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
            public void onFailure(Call<DeleteAccountResponseBodyDto> call, Throwable t)
            {
                responseCallback.failure(t);
            }
        });
    }
}