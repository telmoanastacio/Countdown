package com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.GetCountdownEvent;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

public class CountdownEventsDto
{
    public Map<String, CountdownEventDto> postsIdToEventMap = null;

    private CountdownEventsDto(){}

    public CountdownEventsDto(JSONObject jsonObject)
    {
        new CountdownEventsSerializer(this, jsonObject);
    }
}