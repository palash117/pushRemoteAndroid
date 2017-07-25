package com.example.palash.pushremote.restTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by palash on 22/7/17.
 */

public enum RestType {

    GET(1,"GET"), POST(2,"POST");

    public int value;
    public String httpMethod;

    RestType(int value, String httpMethod){
        this.value = value;
        this.httpMethod = httpMethod;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public static RestType getRestTypeByName(String name){
        RestType returnRestType = null;
        List<RestType> restTypeList = Arrays.asList(RestType.values());
        for(RestType restType: restTypeList){
            if(restType.getHttpMethod().equals(name))
                returnRestType = restType;
        }
        return returnRestType;
    }
}
