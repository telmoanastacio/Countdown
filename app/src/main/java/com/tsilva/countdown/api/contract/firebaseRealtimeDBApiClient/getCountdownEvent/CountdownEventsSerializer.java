package com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

public class CountdownEventsSerializer
{
    private static final String TAG = "CEventsSerializer";

    public CountdownEventsSerializer(CountdownEventsDto countdownEventsDto, JSONObject jsonObject)
    {
        try
        {
            Field postsIdToEventMapField =
                    CountdownEventsDto.class.getField("postsIdToEventMap");
            postsIdToEventMapField.setAccessible(true);

            Iterator<String> keys = jsonObject.keys();
            HashMap<String, CountdownEventDto> postsIdToEventMap = new HashMap<>();

            while(keys.hasNext())
            {
                String key = keys.next();
                postsIdToEventMap.put(key, new CountdownEventDto(jsonObject.getJSONObject(key)));
            }

            postsIdToEventMapField.set(countdownEventsDto, postsIdToEventMap);
        }
        catch(JSONException e)
        {
            Log.d(TAG, "CountdownEventsSerializer: couldn't parse JSON");
        }
        catch(IllegalAccessException e)
        {
            Log.d(TAG, "CountdownEventsSerializer: illegal access");
        }
        catch(NoSuchFieldException e)
        {
            Log.d(TAG, "CountdownEventsSerializer: no such field");
        }
    }
}