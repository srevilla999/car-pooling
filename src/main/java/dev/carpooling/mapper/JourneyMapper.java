package dev.carpooling.mapper;

import dev.carpooling.domain.Journey;
import dev.carpooling.dto.JourneyDto;
import lombok.experimental.UtilityClass;

import static dev.carpooling.domain.Journey.JourneyStatus.AWAITING;

@UtilityClass
public class JourneyMapper {

    public static Journey toJourney(JourneyDto journeyDto, long requestedTime) {
        return Journey.builder()
                .id(journeyDto.getId())
                .people(journeyDto.getPeople())
                .requestedTime(requestedTime)
                .status(AWAITING)
                .build();
    }
}
