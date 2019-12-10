package com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient.SignIn;

/**
 * Created by Telmo Silva on 06.12.2019.
 */

public class SignInResponseBodyDto
{
    public String kind;
    public String localId;
    public String email;
    public String displayName;
    public String idToken;
    public Boolean registered;
    public String refreshToken;
    public Long expiresIn;
}