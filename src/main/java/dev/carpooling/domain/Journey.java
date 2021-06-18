package dev.carpooling.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class Journey {
    int id;
    int people;
    long requestedTime;

    @With
    Car car;

    @With
    JourneyStatus status;

    public enum JourneyStatus {
        AWAITING, TRAVELLING
    }
}
