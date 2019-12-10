package com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.PostCountdownEvent;

import java.util.List;

/**
 * Created by Telmo Silva on 10.12.2019.
 */

public class PostCountdownEventRequestBodyDto
{
    public String details = null;
    public String email = null;
    public String img = null;
    public List<String> shareWith = null;
    public String title = null;
    public Long tsf;
    public Long tsi;

    public PostCountdownEventRequestBodyDto(
            String email,
            String title,
            String details,
            String img,
            List<String> shareWith,
            Long tsi,
            Long tsf)
    {
        this.details = details;
        this.email = email;
        this.img = img;
        this.shareWith = shareWith;
        this.title = title;
        this.tsf = tsf;
        this.tsi = tsi;
    }
}