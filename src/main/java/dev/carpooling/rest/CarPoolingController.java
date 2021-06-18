package dev.carpooling.rest;

import dev.carpooling.domain.Car;
import dev.carpooling.dto.CarDto;
import dev.carpooling.dto.JourneyDto;
import dev.carpooling.dto.JourneyIdDto;
import dev.carpooling.mapper.CarMapper;
import dev.carpooling.service.CarPoolingService;
import dev.carpooling.mapper.JourneyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class CarPoolingController {

    private final CarPoolingService carPoolingService;

    @GetMapping("/status")
    public ResponseEntity<Void> status() {
        return ok().build();
    }

    @PutMapping(path = "/cars", headers = "Content-Type=application/json")
    public ResponseEntity<Void> setCars(@RequestBody Collection<CarDto> carDtos) {
        List<Car> domainCars = carDtos.stream().map(CarMapper::toCar).collect(toList());
        carPoolingService.resetCars(domainCars);
        return ok().build();
    }

    @PostMapping(path = "/journey", headers = "Content-Type=application/json")
    public ResponseEntity<Void> newJourney(@RequestBody JourneyDto journeyDto) {
        carPoolingService.newJourney(JourneyMapper.toJourney(journeyDto, System.currentTimeMillis()));
        return accepted().build();
    }

    @PostMapping(path = "/dropoff", headers = "Content-Type=application/x-www-form-urlencoded")
    public ResponseEntity<Void> dropOffJourney(JourneyIdDto journeyIdDto) {
        carPoolingService.dropOffJourney(journeyIdDto.getID());
        return noContent().build();
    }

    @PostMapping(path = "/locate", headers = "Content-Type=application/x-www-form-urlencoded")
    public ResponseEntity<CarDto> locateJourney(JourneyIdDto journeyIdDto) {
        return carPoolingService.locateJourney(journeyIdDto.getID())
                .map(car -> ok(CarMapper.toCarDto(car)))
                .orElse(noContent().build());
    }

}
