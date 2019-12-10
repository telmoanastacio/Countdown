package com.tsilva.countdown.Api.RestClient.Clients;

import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.PostCountdownEvent.PostCountdownEventRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.PostCountdownEvent.PostCountdownEventResponseBodyDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Telmo Silva on 06.12.2019.
 */

public interface FirebaseRealtimeDBApiClient
{
    @POST("countdownEvents.json")
    Call<PostCountdownEventResponseBodyDto> postCountdownEvent(
            @Query("auth") String API_KEY,
            @Body PostCountdownEventRequestBodyDto postCountdownEventRequestBodyDto);
}