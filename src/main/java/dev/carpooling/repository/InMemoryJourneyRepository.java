package dev.carpooling.repository;

import dev.carpooling.domain.Journey;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static dev.carpooling.domain.Journey.JourneyStatus.AWAITING;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Component
public class InMemoryJourneyRepository implements JourneyRepository {

    private final ConcurrentHashMap<Integer, Journey> journeyDataStore;

    public InMemoryJourneyRepository() {
        journeyDataStore = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<Journey> findJourneyById(int journeyId) {
        return ofNullable(journeyDataStore.get(journeyId));
    }

    public List<Journey> findJourneysStatusAwaitingOrderByTimeAsc() {
        return journeyDataStore.values().parallelStream()
                .filter(journey -> journey.getStatus() == AWAITING)
                .sorted(Comparator.comparingLong(Journey::getRequestedTime))
                .collect(toList());
    }

    @Override
    public void removeAll() {
        journeyDataStore.clear();
    }

    @Override
    public void removeById(int journeyId) {
        journeyDataStore.remove(journeyId);
    }

    @Override
    public void save(Journey journey) {
        journeyDataStore.put(journey.getId(), journey);
    }
}
