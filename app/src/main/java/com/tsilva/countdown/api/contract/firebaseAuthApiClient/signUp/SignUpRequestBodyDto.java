package com.tsilva.countdown.api.contract.firebaseAuthApiClient.signUp;

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