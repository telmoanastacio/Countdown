package com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent;

import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.postCountdownEvent.PostCountdownEventRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.updateCountdownEvent.UpdateCountdownEventRequestBodyDto;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

public class CountdownEventDto implements Serializable
{
    private static final long serialVersionUID = 6491128556778050002L;

    public String email = null;
    public String title = null;
    public String details = null;
    public String img = null;
    public Long tsi = null;
    public Long tsf = null;
    public List<String> shareWith = null;

    public CountdownEventDto() {}

    public CountdownEventDto(JSONObject jsonObject)
    {
        new CountdownEventSerializer(this, jsonObject);
    }

    public CountdownEventDto(PostCountdownEventRequestBodyDto postCountdownEventRequestBodyDto)
    {
        this.email = postCountdownEventRequestBodyDto.email;
        this.title = postCountdownEventRequestBodyDto.title;
        this.details = postCountdownEventRequestBodyDto.details;
        this.img = postCountdownEventRequestBodyDto.img;
        this.tsi = postCountdownEventRequestBodyDto.tsi;
        this.tsf = postCountdownEventRequestBodyDto.tsf;
        this.shareWith = postCountdownEventRequestBodyDto.shareWith;
    }

    public CountdownEventDto(UpdateCountdownEventRequestBodyDto updateCountdownEventRequestBodyDto)
    {
        this.email = updateCountdownEventRequestBodyDto.email;
        this.title = updateCountdownEventRequestBodyDto.title;
        this.details = updateCountdownEventRequestBodyDto.details;
        this.img = updateCountdownEventRequestBodyDto.img;
        this.tsi = updateCountdownEventRequestBodyDto.tsi;
        this.tsf = updateCountdownEventRequestBodyDto.tsf;
        this.shareWith = updateCountdownEventRequestBodyDto.shareWith;
    }
}