package dev.carpooling.planner;

import dev.carpooling.domain.Car;
import dev.carpooling.domain.Journey;
import dev.carpooling.repository.CarRepository;
import dev.carpooling.repository.JourneyRepository;
import dev.carpooling.service.JourneyNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static dev.carpooling.domain.Journey.JourneyStatus.TRAVELLING;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlannerService {

    private final CarRepository carRepository;
    private final JourneyRepository journeyRepository;

    public void newJourney(Journey journey) {
        journeyRepository.save(journey);
    }

    // @Transactional
    // This should be transactional. At least a READ_COMMITTED level of transactionality.
    public void allocateJourneys() {
        List<Journey> awaitingList = journeyRepository.findJourneysStatusAwaitingOrderByTimeAsc();
        if (!awaitingList.isEmpty()) {
            List<Car> availableCars = carRepository.getCarIfJourneyIsNull();

            if (!availableCars.isEmpty()) {
                matchJourneysAndCars(awaitingList, availableCars);
            }
        }
    }

    // @Transactional
    // This should be transactional. At least a READ_COMMITTED level of transactionality.
    public void dropOffJourney(int journeyId) {
        try {
            Journey journey = journeyRepository.findJourneyById(journeyId).orElseThrow(() -> new JourneyNotFoundException(journeyId));

            if (journey.getCar() != null) {
                // Journey was ongoing and now the car is available again
                carRepository.save(journey.getCar().withJourney(null));
            }

            // Journey is removed
            journeyRepository.removeById(journeyId);
        } catch (JourneyNotFoundException ex) {
            log.error("Journey not found: {}", ex.toString());
        }
    }

    // This is a O(n^2) operation
    // For production usages this algorithm has to be improved
    private void matchJourneysAndCars(List<Journey> awaitingList, List<Car> availableCars) {
        for (Journey journey : awaitingList) {
            Optional<Car> carForThisJourney = findCar(availableCars, journey.getPeople());
            if (carForThisJourney.isPresent()) {
                log.info("Car {} is taking journey {}", carForThisJourney.get().getId(), journey.getId());
                carRepository.save(
                        carForThisJourney.get().withJourney(journey)
                );
                journeyRepository.save(journey
                        .withCar(carForThisJourney.get())
                        .withStatus(TRAVELLING));
                availableCars.remove(carForThisJourney.get());
            }
        }
    }

    private Optional<Car> findCar(List<Car> availableCars, int people) {
        return availableCars.stream()
                .filter(car -> car.getSeats() >= people)
                .findAny();
    }


}
