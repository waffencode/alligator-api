package com.alligator.alligatorapi.advice;

import com.alligator.alligatorapi.model.dto.response.ExceptionResponse;
import com.alligator.alligatorapi.exception.PasswordDoesntMatchesException;
import com.alligator.alligatorapi.exception.UsernameAlreadyInUseException;
import com.alligator.alligatorapi.exception.UsernameNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class AllControllersAdvice {
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder builder = new StringBuilder();
        ex.getConstraintViolations()
                .forEach(constraintViolation -> {
                    builder.append(constraintViolation.getMessage());
                    builder.append("; ");
                });

        return new ExceptionResponse(builder.toString());
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            AuthenticationServiceException.class,
            PasswordDoesntMatchesException.class,
            UsernameNotFoundException.class,
            UsernameAlreadyInUseException.class,
            HttpMessageNotReadableException.class,
            IllegalStateException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse simpleHandling(Exception ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ExceptionResponse> handleJsonParseException(JsonParseException ex, WebRequest request) {
        ExceptionResponse errorResponse = new ExceptionResponse("Invalid JSON format: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ExceptionResponse> handleInvalidFormatException(InvalidFormatException ex, WebRequest request) {
        ExceptionResponse errorResponse = new ExceptionResponse("Invalid data format: " + ex.getOriginalMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse("Invalid data format: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
