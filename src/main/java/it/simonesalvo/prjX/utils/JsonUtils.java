package it.simonesalvo.prjX.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Simone Salvo on 22/06/2015.
 * www.simonesalvo.it
 */

public class JsonUtils {
    
    public static String encodeJSON(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T decodeJSON(String json, Type destinationType) {
        return new Gson().fromJson(json, destinationType);
    }
}
