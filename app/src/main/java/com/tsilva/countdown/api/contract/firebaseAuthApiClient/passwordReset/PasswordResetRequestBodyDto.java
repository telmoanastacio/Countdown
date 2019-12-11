package com.tsilva.countdown.api.contract.firebaseAuthApiClient.passwordReset;

/**
 * Created by Telmo Silva on 08.12.2019.
 */

public class PasswordResetRequestBodyDto
{
    public String requestType = "PASSWORD_RESET";
    public String email;

    public PasswordResetRequestBodyDto(String email)
    {
        this.email = email;
    }
}