package com.tsilva.countdown.Api.RestClient.Clients;

import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.PostCountdownEvent.PostCountdownEventRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.PostCountdownEvent.PostCountdownEventResponseBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.UpdateCountdownEvent.UpdateCountdownEventRequestBodyDto;
import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.UpdateCountdownEvent.UpdateCountdownEventResponseBodyDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Telmo Silva on 06.12.2019.
 */

public interface FirebaseRealtimeDBApiClient
{
    @GET("countdownEvents.json")
    Call<ResponseBody> getCountdownEvents(
            @Query("auth") String API_KEY);

    @POST("countdownEvents.json")
    Call<PostCountdownEventResponseBodyDto> postCountdownEvent(
            @Query("auth") String API_KEY,
            @Body PostCountdownEventRequestBodyDto postCountdownEventRequestBodyDto);

    @PATCH("countdownEvents/{postId}.json")
    Call<UpdateCountdownEventResponseBodyDto> updateCountdownEvent(
            @Path("postId") String postId,
            @Query("auth") String API_KEY,
            @Body UpdateCountdownEventRequestBodyDto updateCountdownEventRequestBodyDto);

    @DELETE("countdownEvents/{postId}.json")
    Call<ResponseBody> deleteCountdownEvent(
            @Path("postId") String postId,
            @Query("auth") String API_KEY);
}