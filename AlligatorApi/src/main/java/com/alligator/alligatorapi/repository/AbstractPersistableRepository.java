package com.alligator.alligatorapi.repository;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractPersistableRepository<T extends AbstractPersistable> extends JpaRepository<T, PK> {
}