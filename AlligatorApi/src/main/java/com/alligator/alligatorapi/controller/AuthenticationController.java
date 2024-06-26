package com.alligator.alligatorapi.controller;

import com.alligator.alligatorapi.configuration.security.AuthenticationUserDetails;
import com.alligator.alligatorapi.model.dto.request.AuthenticationRequest;
import com.alligator.alligatorapi.model.dto.request.PasswordChangeRequest;
import com.alligator.alligatorapi.model.dto.request.RegistrationRequest;
import com.alligator.alligatorapi.model.dto.response.ExceptionResponse;
import com.alligator.alligatorapi.model.dto.response.WhoamiResponse;
import com.alligator.alligatorapi.model.entity.user.User;
import com.alligator.alligatorapi.exception.PasswordDoesntMatchesException;
import com.alligator.alligatorapi.exception.UsernameAlreadyInUseException;
import com.alligator.alligatorapi.exception.UsernameNotFoundException;
import com.alligator.alligatorapi.model.entity.user.UserInfo;
import com.alligator.alligatorapi.model.repository.user.UserInfoRepository;
import com.alligator.alligatorapi.service.JwtService;
import com.alligator.alligatorapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final UserInfoRepository userInfoRepository;
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

        UserInfo userInfo = new UserInfo(
                null,
                savedUser,
                request.getFullName(),
                request.getEmail(),
                request.getPhoneNumber()
        );
        userInfoRepository.save(userInfo);

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

    @PostMapping(path = "/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request) {
        String principalUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        userService.changePassword(principalUsername,
                request.getOldPassword(),
                request.getNewPassword());

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/whoami")
    public ResponseEntity<WhoamiResponse> whoami() throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(auth.getDetails() instanceof AuthenticationUserDetails details) {
            Long id = details.getId();
            List<String> roles = auth.getAuthorities().stream()
                    .map(Object::toString)
                    .toList();

            WhoamiResponse response = new WhoamiResponse();
            response.setId(id);
            response.setUsername(username);
            response.setRoles(roles);

            return ResponseEntity.ok(response);
        } else {
            throw new AccessDeniedException("Failed to parse id from principal details.");
        }
    }
}
