package com.alligator.alligatorapi.repository.user;

import com.alligator.alligatorapi.entity.user.User;
import com.alligator.alligatorapi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

// TODO: think about user changes
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}