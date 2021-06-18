package dev.carpooling.repository;

import dev.carpooling.domain.Car;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
public class InMemoryCarRepository implements CarRepository {

    private final ConcurrentHashMap<Integer, Car> carsDataStore;

    public InMemoryCarRepository() {
        carsDataStore = new ConcurrentHashMap<>();
    }

    @Override
    public List<Car> getCarIfJourneyIsNull() {
        return carsDataStore.values().stream()
                .filter(car -> isNull(car.getJourney()))
                .collect(toList());
    }

    @Override
    public void removeAll() {
        carsDataStore.clear();
    }

    @Override
    public void save(Collection<Car> cars) {
        cars.forEach(car -> carsDataStore.put(car.getId(), car));
    }

    @Override
    public void save(Car car) {
        carsDataStore.put(car.getId(), car);
    }
}
