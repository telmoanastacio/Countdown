package com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient;

/**
 * Created by Telmo Silva on 08.12.2019.
 */

public class DeleteAccountRequestBodyDto
{
    public String idToken;

    public DeleteAccountRequestBodyDto(String idToken)
    {
        this.idToken = idToken;
    }
}