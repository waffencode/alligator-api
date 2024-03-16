package com.alligator.alligatorapi.controller;

import com.alligator.alligatorapi.dto.*;
import com.alligator.alligatorapi.entity.User;
import com.alligator.alligatorapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alligator.alligatorapi.service.JwtService;
import com.alligator.alligatorapi.exception.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    private final JwtService jwtService;

    @ExceptionHandler({
            UsernameAlreadyInUseException.class,
            UsernameNotFoundException.class,
            PasswordDoesntMatchesException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ExceptionResponse handleUserExceptions(Exception ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    // register
    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody RegistrationRequest request) {
        User user = new User(
                null,
                request.getUsername(),
                request.getPassword()
        );

        User savedUser = userService.saveToDatabase(user);

        return jwtService.generateTokenFromUsername(savedUser.getUsername());
    }

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public String authenticate(@RequestBody AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (userService.isPasswordCorrect(username, password)) {

            if(!userService.exists(username)) {
                throw new UsernameNotFoundException(username);
            }

            return jwtService.generateTokenFromUsername(username);

        } else {
            throw new PasswordDoesntMatchesException();
        }
    }
}
