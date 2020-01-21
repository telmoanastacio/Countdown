package com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

public class CountdownEventsDto implements Serializable
{
    private static final long serialVersionUID = 6491128556778050001L;

    public Map<String, CountdownEventDto> postsIdToEventMap = null;

    public CountdownEventsDto(){}

    public CountdownEventsDto(JSONObject jsonObject)
    {
        new CountdownEventsSerializer(this, jsonObject);
    }
}