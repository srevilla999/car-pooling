package dev.carpooling.stream;

import dev.carpooling.domain.Journey;

public interface CarPoolingProducer {

    void produceNewJourney(Journey journey);
    void produceDropOff(int journeyId);
    void removeAll();
}
