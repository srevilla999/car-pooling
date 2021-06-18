package dev.carpooling.planner;

import dev.carpooling.domain.Journey;
import dev.carpooling.stream.InMemoryCarPoolingConsumer;
import dev.carpooling.stream.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static dev.carpooling.stream.model.Event.EventType.DROPOFF_JOURNEY;
import static dev.carpooling.stream.model.Event.EventType.NEW_JOURNEY;

@Component
@RequiredArgsConstructor
public class PlannerProcessor {

    private final PlannerProperties properties;
    private final ScheduledExecutorService executorService;

    private final InMemoryCarPoolingConsumer carPoolingConsumer;
    private final PlannerService plannerService;

    @PostConstruct
    public void startThreads() {
        IntStream.range(0, properties.getConsumers()).<Runnable>mapToObj(i -> this::consumeEvent).forEach(executorService::submit);
    }

    private void consumeEvent() {
        Optional<Event> optionalEvent = carPoolingConsumer.consume();
        if (optionalEvent.isEmpty()) {
            // no events - we reschedule
            executorService.schedule(this::consumeEvent, properties.getPollMillis(), TimeUnit.MILLISECONDS);
        } else {
            Event event = optionalEvent.get();

            if (event.getEventType() == NEW_JOURNEY) {
                plannerService.newJourney((Journey) event.getPayload());
            } else if (event.getEventType() == DROPOFF_JOURNEY) {
                plannerService.dropOffJourney((int) event.getPayload());
            }

            plannerService.allocateJourneys();

            executorService.schedule(this::consumeEvent, 0, TimeUnit.MILLISECONDS);
        }
    }
}
