package dev.carpooling.stream;

import dev.carpooling.domain.Journey;
import dev.carpooling.stream.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InMemoryCarPoolingProducer implements CarPoolingProducer {

    private final InMemoryCarPoolingStream carPoolingStream;

    public void produceNewJourney(Journey journey) {
        carPoolingStream.produce(Event.of(Event.EventType.NEW_JOURNEY, journey));
    }

    public void produceDropOff(int journeyId) {
        carPoolingStream.produce(Event.of(Event.EventType.DROPOFF_JOURNEY, journeyId));
    }

    public void removeAll() {
        carPoolingStream.removeAll();
    }
}
