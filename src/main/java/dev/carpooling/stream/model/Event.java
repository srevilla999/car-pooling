package dev.carpooling.stream.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class Event {
    EventType eventType;
    Object payload;

    public enum EventType {
        NEW_JOURNEY, DROPOFF_JOURNEY
    }
}
