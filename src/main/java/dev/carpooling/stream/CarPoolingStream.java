package dev.carpooling.stream;

import dev.carpooling.stream.model.Event;

import java.util.Optional;

public interface CarPoolingStream {

    void produce(Event event);
    Optional<Event> consume();
    void removeAll();

}
