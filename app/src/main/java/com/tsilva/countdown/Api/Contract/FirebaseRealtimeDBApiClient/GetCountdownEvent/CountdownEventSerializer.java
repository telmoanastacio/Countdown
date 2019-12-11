package com.tsilva.countdown.Api.Contract.FirebaseRealtimeDBApiClient.GetCountdownEvent;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

public class CountdownEventSerializer
{
    private static final String TAG = "CEventSerializer";

    public CountdownEventSerializer(CountdownEventDto countdownEventDto, JSONObject jsonObject)
    {
        Map<String, Field> keyToFieldMap = new HashMap<>();

        for(Field field : CountdownEventDto.class.getDeclaredFields())
        {
            field.setAccessible(true);
            if(!Modifier.isFinal(field.getModifiers()))
            {
                keyToFieldMap.put(field.getName(), field);
            }
        }

        for(String key : keyToFieldMap.keySet())
        {
            try
            {
                Field field = keyToFieldMap.get(key);

                if(field == null)
                {
                    continue;
                }

                Class keyClass = CountdownEventDto.class.getField(key).getType();

                if(keyClass.equals(Long.class))
                {
                    field.set(countdownEventDto, jsonObject.getLong(key));
                }
                else if(keyClass.equals(String.class))
                {
                    field.set(countdownEventDto, jsonObject.getString(key));
                }
                else if(keyClass.equals(List.class))
                {
                    JSONArray shareWithJsonArray = jsonObject.getJSONArray(key);
                    List<String> shareWith = (List<String>) field.get(countdownEventDto);
                    int arrayLength = shareWithJsonArray.length();
                    shareWith = new ArrayList<>(arrayLength);

                    if(arrayLength > 0)
                    {
                        for(int i = 0; i < arrayLength; i++)
                        {
                            shareWith.add(shareWithJsonArray.getString(i));
                        }
                    }

                    if(!shareWith.isEmpty())
                    {
                        field.set(countdownEventDto, shareWith);
                    }
                }
            }
            catch(NoSuchFieldException | IllegalAccessException | JSONException e)
            {
                Log.d(TAG, "CountdownEventSerializer: " + key);
            }
        }
    }
}