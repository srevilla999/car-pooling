package dev.carpooling.repository;

import dev.carpooling.domain.Journey;

import java.util.List;
import java.util.Optional;

public interface JourneyRepository {

    Optional<Journey> findJourneyById(int journeyId);
    List<Journey> findJourneysStatusAwaitingOrderByTimeAsc();

    void save(Journey journey);

    void removeAll();
    void removeById(int journeyId);
}
