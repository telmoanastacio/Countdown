package com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient;

/**
 * Created by Telmo Silva on 06.12.2019.
 */

public class SignInResponseBodyDto
{
    String kind;
    String localId;
    String email;
    String displayName;
    String idToken;
    Boolean registered;
    String refreshToken;
    Long expiresIn;
}