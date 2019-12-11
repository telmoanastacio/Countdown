package com.tsilva.countdown.api.requests.post;

import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.postCountdownEvent.PostCountdownEventRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.postCountdownEvent.PostCountdownEventResponseBodyDto;
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

public final class PostFirebaseRealtimeDBApiClientPostCountdownEvent
{
    private FirebaseRealtimeDBApiClient firebaseRealtimeDBApiClient = null;

    public PostFirebaseRealtimeDBApiClientPostCountdownEvent(
            FirebaseRealtimeDBApiClient firebaseRealtimeDBApiClient)
    {
        this.firebaseRealtimeDBApiClient = firebaseRealtimeDBApiClient;
    }

    public void execute(PostCountdownEventRequestBodyDto postCountdownEventRequestBodyDto,
                        final ResponseCallback<PostCountdownEventResponseBodyDto> responseCallback)
    {
        firebaseRealtimeDBApiClient
                .postCountdownEvent(RestClientConfiguration.FIREBASE_REALTIME_DB_API_KEY,
                                    postCountdownEventRequestBodyDto)
                .enqueue(new Callback<PostCountdownEventResponseBodyDto>()
        {
            @Override
            public void onResponse(Call<PostCountdownEventResponseBodyDto> call,
                                   Response<PostCountdownEventResponseBodyDto> response)
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
            public void onFailure(Call<PostCountdownEventResponseBodyDto> call, Throwable t)
            {
                responseCallback.failure(t);
            }
        });
    }
}