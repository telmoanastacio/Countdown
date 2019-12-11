package com.tsilva.countdown.api.requests.post;

import com.tsilva.countdown.api.contract.firebaseAuthApiClient.deleteAccount.DeleteAccountRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.deleteAccount.DeleteAccountResponseBodyDto;
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