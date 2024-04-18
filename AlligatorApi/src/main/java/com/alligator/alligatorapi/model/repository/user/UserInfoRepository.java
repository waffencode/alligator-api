package com.alligator.alligatorapi.model.repository.user;

import com.alligator.alligatorapi.model.entity.user.User;
import com.alligator.alligatorapi.model.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    void deleteByUser(User user);
}