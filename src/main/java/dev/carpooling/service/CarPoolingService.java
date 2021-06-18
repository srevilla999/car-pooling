package dev.carpooling.service;

import dev.carpooling.domain.Car;
import dev.carpooling.domain.Journey;
import dev.carpooling.repository.CarRepository;
import dev.carpooling.repository.JourneyRepository;
import dev.carpooling.stream.InMemoryCarPoolingProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarPoolingService {

    private final CarRepository carRepository;
    private final JourneyRepository journeyRepository;
    private final InMemoryCarPoolingProducer carPoolingProducer;

    public void resetCars(Collection<Car> cars) {
        journeyRepository.removeAll();
        carRepository.removeAll();

        carPoolingProducer.removeAll();

        carRepository.save(cars);

        log.info("State has been reset. Car fleet of {} cars.", cars.size());
    }

    public void newJourney(Journey journey) {
        carPoolingProducer.produceNewJourney(journey);
    }

    public void dropOffJourney(int journeyId) {
        journeyRepository.findJourneyById(journeyId).orElseThrow(() -> new JourneyNotFoundException(journeyId));
        carPoolingProducer.produceDropOff(journeyId);
    }

    public Optional<Car> locateJourney(int journeyId) {
        Journey journey = journeyRepository.findJourneyById(journeyId).orElseThrow(() -> new JourneyNotFoundException(journeyId));

        if (journey.getStatus() == Journey.JourneyStatus.TRAVELLING && journey.getCar() != null) {
            return of(journey.getCar());
        } else {
            return empty();
        }
    }
}
