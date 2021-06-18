package dev.carpooling.stream;

import dev.carpooling.stream.model.Event;

import java.util.Optional;

public interface CarPoolingConsumer {

    Optional<Event> consume();
}
