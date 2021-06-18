package dev.carpooling.service;

import static java.lang.String.format;

public class JourneyNotFoundException extends RuntimeException {
    public JourneyNotFoundException(int journeyId) {
        super(format("Journey %d is not found", journeyId));
    }
}
