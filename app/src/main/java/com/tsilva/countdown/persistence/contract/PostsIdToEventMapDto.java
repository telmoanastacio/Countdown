package com.tsilva.countdown.persistence.contract;

import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Telmo Silva on 23.01.2020.
 */

public class PostsIdToEventMapDto implements Serializable
{
    private static final long serialVersionUID = 6491128556778050000L;

    public Map<String, CountdownEventDto> postsIdToEventMap = null;

    public PostsIdToEventMapDto(Map<String, CountdownEventDto> postsIdToEventMap)
    {
        this.postsIdToEventMap = postsIdToEventMap;
    }
}