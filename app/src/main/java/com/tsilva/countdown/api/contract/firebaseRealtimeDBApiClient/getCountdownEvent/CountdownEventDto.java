package com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

public class CountdownEventDto
{
    public String email = null;
    public String title = null;
    public String details = null;
    public String img = null;
    public Long tsi = null;
    public Long tsf = null;
    public List<String> shareWith = null;

    private CountdownEventDto(){}

    public CountdownEventDto(JSONObject jsonObject)
    {
        new CountdownEventSerializer(this, jsonObject);
    }
}