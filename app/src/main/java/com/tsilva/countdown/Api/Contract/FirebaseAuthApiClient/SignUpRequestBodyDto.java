package com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient;

/**
 * Created by Telmo Silva on 06.12.2019.
 */

public class SignUpRequestBodyDto
{
    String email;
    String password;
    boolean returnSecureToken = true;

    public SignUpRequestBodyDto(String email, String password)
    {
        this.email = email;
        this.password = password;
    }
}