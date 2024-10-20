package com.jobseeker.cluedetectivenotes.utils;

import java.util.HashMap;
import java.util.Map;

public class DataMap {
    private HashMap hashMap;
    private String rtnMsg = " is not exist";
    public DataMap(){
        hashMap = new HashMap();
    }

    public void putString(String key, String value){
        hashMap.put(key,value);
    }

    public String getString(String key){
        if(hashMap.containsKey(key)){
            return (String)hashMap.get(key);
        }else{
            return key+rtnMsg;
        }
    }

    public Map<String,String> getMap(){
        return hashMap;
    }
}
