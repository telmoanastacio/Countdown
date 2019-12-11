package com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent;

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