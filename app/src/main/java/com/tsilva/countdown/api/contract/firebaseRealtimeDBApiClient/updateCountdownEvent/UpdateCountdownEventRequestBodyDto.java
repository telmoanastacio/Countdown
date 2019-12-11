package com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.updateCountdownEvent;

import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.postCountdownEvent.PostCountdownEventRequestBodyDto;

import java.util.List;

/**
 * Created by Telmo Silva on 10.12.2019.
 */

public class UpdateCountdownEventRequestBodyDto extends PostCountdownEventRequestBodyDto
{
    public UpdateCountdownEventRequestBodyDto(
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