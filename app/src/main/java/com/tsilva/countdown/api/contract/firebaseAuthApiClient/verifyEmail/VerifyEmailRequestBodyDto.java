package com.tsilva.countdown.api.contract.firebaseAuthApiClient.verifyEmail;

/**
 * Created by Telmo Silva on 07.12.2019.
 */

public class VerifyEmailRequestBodyDto
{
    public String requestType = "VERIFY_EMAIL";
    public String idToken;

    public VerifyEmailRequestBodyDto(String idToken)
    {
        this.idToken = idToken;
    }
}