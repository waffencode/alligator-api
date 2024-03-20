package com.alligator.alligatorapi.controller;

import com.alligator.alligatorapi.dto.request.AuthenticationRequest;
import com.alligator.alligatorapi.dto.request.RegistrationRequest;
import com.alligator.alligatorapi.dto.response.ExceptionResponse;
import com.alligator.alligatorapi.dto.response.WhoamiResponse;
import com.alligator.alligatorapi.entity.User;
import com.alligator.alligatorapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.alligator.alligatorapi.service.JwtService;
import com.alligator.alligatorapi.exception.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping(path = "/whoami")
    public ResponseEntity<WhoamiResponse> whoami() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Long id = (Long)((Map<String, Object>)auth.getDetails()).get("id");
        List<String> roles = auth.getAuthorities().stream()
                .map(Object::toString)
                .toList();

        WhoamiResponse response = new WhoamiResponse();
        response.setId(id);
        response.setUsername(username);
        response.setRoles(roles);

        return ResponseEntity.ok(response);
    }
}
