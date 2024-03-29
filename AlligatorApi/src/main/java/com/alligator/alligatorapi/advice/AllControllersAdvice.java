package com.alligator.alligatorapi.advice;

import com.alligator.alligatorapi.dto.response.ExceptionResponse;
import com.alligator.alligatorapi.exception.PasswordDoesntMatchesException;
import com.alligator.alligatorapi.exception.UsernameAlreadyInUseException;
import com.alligator.alligatorapi.exception.UsernameNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
            HttpMessageNotReadableException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse simpleHandling(Exception ex) {
        return new ExceptionResponse(ex.getMessage());
    }
}
