package com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignUp;

/**
 * Created by Telmo Silva on 06.12.2019.
 */

public class SignUpRequestBodyDto
{
    public String email;
    public String password;
    public boolean returnSecureToken = true;

    public SignUpRequestBodyDto(String email, String password)
    {
        this.email = email;
        this.password = password;
    }
}