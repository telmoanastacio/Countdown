package com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient;

/**
 * Created by Telmo Silva on 07.12.2019.
 */

public class SignInRequestBodyDto
{
    String email;
    String password;
    boolean returnSecureToken = true;

    public SignInRequestBodyDto(String email, String password)
    {
        this.email = email;
        this.password = password;
    }
}