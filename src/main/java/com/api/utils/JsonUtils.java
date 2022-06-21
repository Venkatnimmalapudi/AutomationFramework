package com.api.utils;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class JsonUtils {
    public static void validateJsonObjectSchema(String jsonFromAPI, InputStream jsonPath) throws Exception {
        JSONObject jsonSchema = new JSONObject(new JSONTokener(jsonPath));
        JSONObject jsonSubject = new JSONObject(new JSONTokener(jsonFromAPI));
        Schema schema = SchemaLoader.load(jsonSchema);
        try {
            schema.validate(jsonSubject);
        } catch (ValidationException e){
            e.printStackTrace();
            throw new Exception("Json validation failed! " + e.getMessage() + "\n" + jsonFromAPI);
        }
    }


    public static boolean areEqual(final JSONObject json1, final JSONObject json2) {
        //null check
        if(json1 == null && json2 == null) {
            return true;
        } else if(json1 == null || json2 == null) {
            return false;
        }

        //iterate over keys of json 1
        //Every key in Json1 should be present in Json2
        //Every value of Json1 should be matching with Json2
        for(String key : json1.keySet()) {
            if(!json2.has(key)) {
                return false;
            }

            Object json1Value = json1.get(key);
            Object json2Value = json2.get(key);

            if(json1Value instanceof JSONObject) {
                if(!(json2Value instanceof JSONObject)) {
                    return false;
                } else if (!areEqual((JSONObject) json1Value, (JSONObject) json2Value)) {
                    return false;
                }
            } else if(json1Value instanceof JSONArray) {
                if(!(json2Value instanceof JSONArray)) {
                    return false;
                } else if(!JSONArrayEquals((JSONArray) json1Value, (JSONArray) json2Value)) {
                    return false;
                }
            } else if(!json2.get(key).equals(json1.get(key))) {
                return false;
            }
        }

        return true;
    }

    public static boolean JSONArrayEquals(final JSONArray array1, final JSONArray array2) {
        //length check
        if(array1.length() != array2.length()) {
            return false;
        }

        for(int i = 0; i < array1.length(); i++) {
            Object value1 = array1.get(i);
            Object value2 = array2.get(i);

            if(value1 instanceof JSONObject) {
                if(!(value2 instanceof JSONObject)) {
                    return false;
                } else if (!areEqual((JSONObject) value1, (JSONObject) value2)) {
                    return false;
                }
            } else if(value1 instanceof JSONArray) {
                if(!(value2 instanceof JSONArray)) {
                    return false;
                } else if(!JSONArrayEquals((JSONArray) value1, (JSONArray) value2)) {
                    return false;
                }
            } else if (!value1.equals(value2)) {
                return false;
            }
        }

        for(int i = 0; i < array2.length(); i++) {
            Object value1 = array1.get(i);
            Object value2 = array2.get(i);

            if(value2 instanceof JSONObject) {
                if(!(value1 instanceof JSONObject)) {
                    return false;
                } else if (!areEqual((JSONObject) value2, (JSONObject) value1)) {
                    return false;
                }
            } else if(value2 instanceof JSONArray) {
                if(!(value1 instanceof JSONArray)) {
                    return false;
                } else if(!JSONArrayEquals((JSONArray) value2, (JSONArray) value1)) {
                    return false;
                }
            } else if (!value2.equals(value1)) {
                return false;
            }
        }

        return true;
    }
}
