package dev.carpooling.mapper;

import dev.carpooling.domain.Car;
import dev.carpooling.dto.CarDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CarMapper {

    public static Car toCar(CarDto carDto) {
        return Car.builder()
                .id(carDto.getId())
                .seats(carDto.getSeats())
                .build();
    }

    public static CarDto toCarDto(Car car) {
        return new CarDto(car.getId(), car.getSeats());
    }
}
