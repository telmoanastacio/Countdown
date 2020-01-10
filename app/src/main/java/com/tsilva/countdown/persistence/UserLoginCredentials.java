package com.tsilva.countdown.persistence;

import android.text.TextUtils;

import com.tsilva.countdown.persistence.sharedPreferences.SharedPreferencesOperations;

/**
 * Created by Telmo Silva on 12.12.2019.
 */

public final class UserLoginCredentials
{
    private final static String EMAIL = "EMAIL";
    private final static String PASSWORD = "PASSWORD";
    private final static String ID_TOKEN = "ID_TOKEN";

    private SharedPreferencesOperations sharedPreferencesOperations;

    public UserLoginCredentials(SharedPreferencesOperations sharedPreferencesOperations)
    {
        this.sharedPreferencesOperations = sharedPreferencesOperations;
    }

    public void setLastUsedCredentials(String userName, String password)
    {
        sharedPreferencesOperations.putString(EMAIL, userName);
        sharedPreferencesOperations.putString(PASSWORD, password);
    }

    public void setIdToken(String idToken)
    {
        sharedPreferencesOperations.putString(ID_TOKEN, idToken);
    }

    public void clearCredencials()
    {
        sharedPreferencesOperations.putString(EMAIL, null);
        sharedPreferencesOperations.putString(PASSWORD, null);
        sharedPreferencesOperations.putString(ID_TOKEN, null);
    }

    public boolean hasLastStoredCredentials()
    {
        String userName = sharedPreferencesOperations.getString(EMAIL);
        String password = sharedPreferencesOperations.getString(PASSWORD);

        return !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password);
    }

    public String getEmail()
    {
        return sharedPreferencesOperations.getString(EMAIL);
    }

    public String getPassword()
    {
        return sharedPreferencesOperations.getString(PASSWORD);
    }

    public String getIdToken()
    {
        return sharedPreferencesOperations.getString(ID_TOKEN);
    }
}