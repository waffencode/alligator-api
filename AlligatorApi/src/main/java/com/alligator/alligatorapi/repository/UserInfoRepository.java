package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.UserInfo;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Override
    @PreAuthorize("@securityService.validateUsernameSameAsPrincipal(#userInfo.user.username)")
    UserInfo save(@Param("userInfo") UserInfo userInfo);
}