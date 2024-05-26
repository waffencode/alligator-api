package com.alligator.alligatorapi.model.repository.user;

import com.alligator.alligatorapi.model.entity.user.User;
import com.alligator.alligatorapi.model.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, QuerydslPredicateExecutor<UserInfo> {
    void deleteByUser(User user);
    UserInfo getByUserId(Long userId);
    Optional<UserInfo> findByUserId(Long userId);
    Boolean existsByUser(User user);
}