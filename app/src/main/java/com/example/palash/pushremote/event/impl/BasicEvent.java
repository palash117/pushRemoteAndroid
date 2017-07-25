package com.example.palash.pushremote.event.impl;


import com.example.palash.pushremote.event.Eventable;
import com.example.palash.pushremote.event.type.EventType;

/**
 * Created by palash on 12/7/17.
 */
public class BasicEvent implements Eventable {

    public String destination;

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public EventType type;

    public String link;

    @Override
    public Object process() {
        return null;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
