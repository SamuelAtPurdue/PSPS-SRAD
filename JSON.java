package com.psps;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSON {
    /**
     * A class for connecting the json-simple library
     * Keeps all functionality separate from the other classes.
     */
    private JSONObject jsonObject;

    public JSON(){
        jsonObject = new JSONObject();
    }

    /**
     * Adds a value in the form "key" : value
     * @param key
     * @param value
     */
    public void addValue (String key, Object value){
        jsonObject.put(key, value);
    }

    /**
     * Adds an array to the json object
     * @param key
     * @param values
     */
    public void addArray(String key, Object[] values){
        JSONArray array = new JSONArray();
        for (Object value : values)
            array.add(value);
        jsonObject.put(key, array);
    }

    //toString override
    @Override
    public String toString() {
        return jsonObject.toJSONString();
    }
}
