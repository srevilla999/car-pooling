package dev.carpooling.stream;

import dev.carpooling.stream.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Component
public class InMemoryCarPoolingStream implements CarPoolingStream {

    private final ConcurrentLinkedQueue<Event> stream;

    public InMemoryCarPoolingStream() {
        stream = new ConcurrentLinkedQueue<>();
    }

    public void produce(Event event) {
        log.debug("Produced a new event: {}", event);
        stream.add(event);
    }

    public Optional<Event> consume() {
        return Optional.ofNullable(stream.poll());
    }

    public void removeAll() {
        stream.clear();
    }
}
