package com.tsilva.countdown.Api.Contract.FirebaseAuthApiClient;

/**
 * Created by Telmo Silva on 06.12.2019.
 */

public class SignUpResponseBodyDto
{
    String kind;
    String idToken;
    String email;
    String refreshToken;
    Long expiresIn;
    String localId;
}