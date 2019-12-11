package com.tsilva.countdown.api.requests.patch;

import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.updateCountdownEvent.UpdateCountdownEventRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.updateCountdownEvent.UpdateCountdownEventResponseBodyDto;
import com.tsilva.countdown.api.requests.ResponseUtils;
import com.tsilva.countdown.api.restClient.ResponseCallback;
import com.tsilva.countdown.api.restClient.RestClientConfiguration;
import com.tsilva.countdown.api.restClient.clients.FirebaseRealtimeDBApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Telmo Silva on 07.12.2019.
 */

public final class PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent
{
    private FirebaseRealtimeDBApiClient firebaseRealtimeDBApiClient = null;

    public PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent(
            FirebaseRealtimeDBApiClient firebaseRealtimeDBApiClient)
    {
        this.firebaseRealtimeDBApiClient = firebaseRealtimeDBApiClient;
    }

    public void execute(String postId,
                        UpdateCountdownEventRequestBodyDto updateCountdownEventRequestBodyDto,
                        final ResponseCallback<UpdateCountdownEventResponseBodyDto> responseCallback)
    {
        firebaseRealtimeDBApiClient
                .updateCountdownEvent(postId,
                                      RestClientConfiguration.FIREBASE_REALTIME_DB_API_KEY,
                                      updateCountdownEventRequestBodyDto)
                .enqueue(new Callback<UpdateCountdownEventResponseBodyDto>()
        {
            @Override
            public void onResponse(Call<UpdateCountdownEventResponseBodyDto> call,
                                   Response<UpdateCountdownEventResponseBodyDto> response)
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
            public void onFailure(Call<UpdateCountdownEventResponseBodyDto> call,
                                  Throwable t)
            {
                responseCallback.failure(t);
            }
        });
    }
}