package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.model.entity.user.Role;
import com.alligator.alligatorapi.model.entity.user.User;
import com.alligator.alligatorapi.model.entity.user.UserRole;
import com.alligator.alligatorapi.exception.PasswordDoesntMatchesException;
import com.alligator.alligatorapi.exception.UsernameAlreadyInUseException;
import com.alligator.alligatorapi.exception.UsernameNotFoundException;
import com.alligator.alligatorapi.model.repository.user.UserRepository;
import com.alligator.alligatorapi.model.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder encoder;

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public User saveToDatabase(User user) throws UsernameAlreadyInUseException {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new UsernameAlreadyInUseException(user.getUsername());

        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User loadFromDatabase(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty())
            throw new UsernameNotFoundException(username);

        return userOptional.get();
    }

    public List<Role> loadRolesFromDatabase(User user) {
        return userRoleRepository.findAllByUser(user).stream().map(UserRole::getRole).toList();
    }

    public Boolean exists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isPasswordCorrect(String username, String password) throws UsernameNotFoundException {
        User databaseUser = loadFromDatabase(username);

        try {
            return encoder.matches(password, databaseUser.getPassword());
        } catch (IllegalArgumentException e) {
            Logger.getLogger(UserService.class.getName()).warning("Encoder got null raw password!");
            return false;
        }
    }

    public void changePassword(String username, String oldPassword, String newPassword) throws UsernameNotFoundException, PasswordDoesntMatchesException {
        User user = loadFromDatabase(username);

        if(!isPasswordCorrect(username, oldPassword))
            throw new PasswordDoesntMatchesException();

        user.setPassword(encoder.encode(newPassword));

        userRepository.save(user);
    }
}
