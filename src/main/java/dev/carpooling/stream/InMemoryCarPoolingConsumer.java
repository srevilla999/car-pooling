package dev.carpooling.stream;

import dev.carpooling.stream.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InMemoryCarPoolingConsumer implements CarPoolingConsumer {

    private final InMemoryCarPoolingStream carPoolingStream;

    public Optional<Event> consume() {
        return carPoolingStream.consume();
    }
}
