package com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.UpdateCountdownEvent;

import com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.PostCountdownEvent.PostCountdownEventRequestBodyDto;

import java.util.List;

/**
 * Created by Telmo Silva on 10.12.2019.
 */

public class UpdateCountdownEventResponseBodyDto extends PostCountdownEventRequestBodyDto
{
    public UpdateCountdownEventResponseBodyDto(
            String email,
            String title,
            String details,
            String img,
            List<String> shareWith,
            Long tsi,
            Long tsf)
    {
        super(email, title, details, img, shareWith, tsi, tsf);
    }
}