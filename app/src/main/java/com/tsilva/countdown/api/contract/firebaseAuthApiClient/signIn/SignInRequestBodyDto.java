package com.tsilva.countdown.api.contract.firebaseAuthApiClient.signIn;

/**
 * Created by Telmo Silva on 07.12.2019.
 */

public class SignInRequestBodyDto
{
    public String email;
    public String password;
    public boolean returnSecureToken = true;

    public SignInRequestBodyDto(String email, String password)
    {
        this.email = email;
        this.password = password;
    }
}