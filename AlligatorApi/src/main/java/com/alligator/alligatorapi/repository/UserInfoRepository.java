package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}