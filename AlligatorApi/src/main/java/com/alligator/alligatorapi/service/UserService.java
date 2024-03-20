package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.entity.Role;
import com.alligator.alligatorapi.entity.User;
import com.alligator.alligatorapi.exception.UsernameAlreadyInUseException;
import com.alligator.alligatorapi.exception.UsernameNotFoundException;
import com.alligator.alligatorapi.repository.RoleRepository;
import com.alligator.alligatorapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder encoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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
        return roleRepository.findAllByUser(user);
    }

    public Boolean exists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isPasswordCorrect(String username, String password) throws UsernameNotFoundException {
        User databaseUser = loadFromDatabase(username);
        return encoder.matches(password, databaseUser.getPassword());
    }
}
