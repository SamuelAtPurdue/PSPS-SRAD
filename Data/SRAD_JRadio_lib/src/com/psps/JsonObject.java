package com.psps;

import java.util.HashMap;

/**
 * Created by Samuel Hild on 10/7/2018.
 */
class JsonObject {

    private static final String JSON_HEADER = "%s = {";

    private String name;
    private HashMap<String,Object> jsonData;

    JsonObject(String name, HashMap<String, Object> values){
        this.name = name;
    }

    public void add(String name, Object value){
        jsonData.put(name, value);
    }

    //Object() overrides
    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    public HashMap<String, Object> getJsonData(){
        return jsonData;
    }

    public String[] getKeyList(){
        String[] string = new String[jsonData.keySet().size()];
        return jsonData.keySet().toArray(string);
    }

    public Object getValueFromKey(String key){
        return jsonData.get(key);
    }
    /*
    this.name={

    }
     */

}

