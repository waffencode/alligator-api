package com.alligator.alligatorapi.repository.user;

import com.alligator.alligatorapi.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Override
    @PreAuthorize("@securityService.validateUsernameSameAsPrincipal(#userInfo.user.username)")
    UserInfo save(@Param("userInfo") UserInfo userInfo);
}