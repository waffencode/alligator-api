package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}