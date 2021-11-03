package com.example.messenger.utils;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.runtime.Settings;

import java.io.IOException;

public class Utils
{

    private final static DslJson<Object> dslJson = new DslJson<>(Settings.basicSetup());

    public static String toDslJson(Object object)
    {
        JsonWriter writer = dslJson.newWriter();

        try
        {
            dslJson.serialize(writer, object);
            return writer.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromDslJson(Class<T> type, String json)
    {
        try
        {
            return dslJson.deserialize(type, json.getBytes(), json.getBytes().length);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
