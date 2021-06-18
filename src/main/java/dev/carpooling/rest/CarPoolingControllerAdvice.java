package dev.carpooling.rest;

import dev.carpooling.service.JourneyNotFoundException;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;

@RestControllerAdvice
public class CarPoolingControllerAdvice {

    /**
     * We can argue whether we need this or not, but the exercise explicitly said that a missing header
     * should lead to a 400 instead of a 415
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<HttpError> badContentTypeHeader() {
        return badRequest()
                .body(HttpError.builder().message("Bad header").build());
    }

    @ExceptionHandler({JourneyNotFoundException.class})
    public ResponseEntity<Void> journeyNotFound() {
        return notFound().build();
    }

    @Value
    @Builder
    private static class HttpError {
        String message;
    }
}
