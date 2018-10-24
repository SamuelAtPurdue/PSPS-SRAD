package com.psps;

import java.util.HashMap;
import java.util.Map;

enum Type {
    FLIGHT("flightdatasequence.csv"),
    STATUS("statusdatasequence.csv");

    private final String filename;
    private Map<Integer, String> mapping = new HashMap<>();

    Type(String filename){
        this.filename = filename;
    }

    public void putIntoMap(int index, String value){
        mapping.put(index, value);
    }

    public Map<Integer, String> getMapping(){
        return mapping;
    }
    public String getFilePath(){
        return this.filename;
    }
}