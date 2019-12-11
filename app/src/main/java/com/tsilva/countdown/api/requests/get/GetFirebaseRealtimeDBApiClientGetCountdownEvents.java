package com.tsilva.countdown.api.requests.get;

import com.tsilva.countdown.api.requests.ResponseUtils;
import com.tsilva.countdown.api.restClient.ResponseCallback;
import com.tsilva.countdown.api.restClient.RestClientConfiguration;
import com.tsilva.countdown.api.restClient.clients.FirebaseRealtimeDBApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Telmo Silva on 07.12.2019.
 */

public final class GetFirebaseRealtimeDBApiClientGetCountdownEvents
{
    private FirebaseRealtimeDBApiClient firebaseRealtimeDBApiClient = null;

    public GetFirebaseRealtimeDBApiClientGetCountdownEvents(
            FirebaseRealtimeDBApiClient firebaseRealtimeDBApiClient)
    {
        this.firebaseRealtimeDBApiClient = firebaseRealtimeDBApiClient;
    }

    public void execute(final ResponseCallback<ResponseBody> responseCallback)
    {
        firebaseRealtimeDBApiClient
                .getCountdownEvents(RestClientConfiguration.FIREBASE_REALTIME_DB_API_KEY)
                .enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
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
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                responseCallback.failure(t);
            }
        });
    }
}