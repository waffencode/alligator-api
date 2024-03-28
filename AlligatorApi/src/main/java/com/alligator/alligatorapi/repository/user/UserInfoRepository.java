package com.alligator.alligatorapi.repository.user;

import com.alligator.alligatorapi.entity.user.User;
import com.alligator.alligatorapi.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    void deleteByUser(User user);
}