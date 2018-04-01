package com.example.palash.pushremote.event.impl;

import com.example.palash.pushremote.event.Eventable;
import com.example.palash.pushremote.event.type.EventType;

public class VolumeEvent implements Eventable {

    public String destination;

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }


    public EventType type;

    public int volumePercentage;

    public int getVolumePercentage() {
        return volumePercentage;
    }

    public void setVolumePercentage(int volumePercentage) {
        this.volumePercentage = volumePercentage;
    }

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
