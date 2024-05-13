package com.alligator.alligatorapi.model.repository.user;

import com.alligator.alligatorapi.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// TODO: think about user changes
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}