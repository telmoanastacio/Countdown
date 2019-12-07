package com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient;

/**
 * Created by Telmo Silva on 06.12.2019.
 */

public class SignUpResponseBodyDto
{
    public String kind;
    public String idToken;
    public String email;
    public String refreshToken;
    public Long expiresIn;
    public String localId;
}