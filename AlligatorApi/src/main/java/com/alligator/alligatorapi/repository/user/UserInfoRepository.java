package com.alligator.alligatorapi.repository.user;

import com.alligator.alligatorapi.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @PreAuthorize("@securityService.validateUsernameSameAsPrincipal(userInfo.user.username)")
    <S extends UserInfo> S save(S userInfo);
}