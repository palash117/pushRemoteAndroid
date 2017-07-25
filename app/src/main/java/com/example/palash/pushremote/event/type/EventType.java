package com.example.palash.pushremote.event.type;

/**
 * Created by palash on 17/7/17.
 */
public enum EventType {
    YOUTUBE("YOUTUBE",1);
    String type;
    int value;
    EventType(String type, int value){
        this.value = value;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
