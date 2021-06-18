package dev.carpooling.repository;

import dev.carpooling.domain.Car;

import java.util.Collection;
import java.util.List;

public interface CarRepository {

    List<Car> getCarIfJourneyIsNull();

    void removeAll();
    void save(Collection<Car> cars);
    void save(Car car);
}
