package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}