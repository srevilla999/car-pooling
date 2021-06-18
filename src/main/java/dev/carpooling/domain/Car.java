package dev.carpooling.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class Car {
    int id;
    int seats;

    @With
    Journey journey;
}
