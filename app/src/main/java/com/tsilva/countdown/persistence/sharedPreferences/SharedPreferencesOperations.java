package com.tsilva.countdown.persistence.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Telmo Silva on 12.12.2019.
 */

public final class SharedPreferencesOperations
{
    private static final String COUNTDOWN = "COUNTDOWN";

    private Context context;

    private SharedPreferencesOperations() {}

    public SharedPreferencesOperations(Context context)
    {
        this.context = context;
    }

    private SharedPreferences.Editor getEditor()
    {
        return getPreferences().edit();
    }

    private SharedPreferences getPreferences()
    {
        return context.getSharedPreferences(COUNTDOWN, Context.MODE_PRIVATE);
    }

    public void putBoolean(String key, boolean value)
    {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putInt(String key, int value)
    {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putLong(String key, long value)
    {
        SharedPreferences.Editor editor = getEditor();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putFloat(String key, float value)
    {
        SharedPreferences.Editor editor = getEditor();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void putString(String key, String value)
    {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key)
    {
        return getPreferences().getBoolean(key, false);
    }

    public int getInt(String key)
    {
        return getPreferences().getInt(key, -1);
    }

    public long getLong(String key)
    {
        return getPreferences().getLong(key, -1L);
    }

    public float getFloat(String key)
    {
        return getPreferences().getFloat(key, Float.NEGATIVE_INFINITY);
    }

    public String getString(String key)
    {
        return getPreferences().getString(key, null);
    }
}