package com.sanjeet.marshmallow.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerErrorHandler {

    @ExceptionHandler(ParameterParsingException.class)
    public ResponseEntity<Object> handleParameterParsingException(ParameterParsingException ex, WebRequest request) {
        return generateBadRequestResponse(ex, request.getParameterMap());
    }

    @ExceptionHandler(RobotAgroundException.class)
    public ResponseEntity<Object> handleRobotAgroundException(RobotAgroundException ex, WebRequest request) {
        return generateBadRequestResponse(ex, request.getParameterMap());
    }

    private ResponseEntity<Object> generateBadRequestResponse(Exception ex, Map<String, String[]> requestParameters) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.putAll(requestParameters);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
