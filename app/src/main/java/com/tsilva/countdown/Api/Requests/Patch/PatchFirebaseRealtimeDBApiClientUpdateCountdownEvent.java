package com.tsilva.countdown.Api.Requests.Patch;

import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.PostCountdownEvent.PostCountdownEventRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.PostCountdownEvent.PostCountdownEventResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.UpdateCountDownEvent.UpdateCountdownEventRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.UpdateCountDownEvent.UpdateCountdownEventResponseBodyDto;
import com.tsilva.countdown.Api.Requests.ResponseUtils;
import com.tsilva.countdown.Api.RestClient.Clients.FirebaseRealtimeDBApiClient;
import com.tsilva.countdown.Api.RestClient.ResponseCallback;
import com.tsilva.countdown.Api.RestClient.RestClientConfiguration;

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