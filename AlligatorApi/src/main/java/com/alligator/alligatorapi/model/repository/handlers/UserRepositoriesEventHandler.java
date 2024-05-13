package com.alligator.alligatorapi.model.repository.handlers;

import com.alligator.alligatorapi.model.entity.enums.RoleName;
import com.alligator.alligatorapi.model.entity.user.Role;
import com.alligator.alligatorapi.model.entity.user.User;
import com.alligator.alligatorapi.model.entity.user.UserInfo;
import com.alligator.alligatorapi.model.entity.user.UserRole;
import com.alligator.alligatorapi.model.repository.user.UserInfoRepository;
import com.alligator.alligatorapi.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
@RepositoryEventHandler
public class UserRepositoriesEventHandler {
    private final SecurityService securityService;
    private final UserInfoRepository userInfoRepository;

    @HandleBeforeSave
    public void handleUserSave(User user) throws AccessDeniedException {
        if(!securityService.isUsernameSameAsPrincipal(user.getUsername()))
            throw new AccessDeniedException(STR . "Only user \{user.getUsername()} can manage himself");
    }

    /**
     * Creates user info entity for every user registration
     * @param user {@link User}
     */
    @HandleAfterCreate
    public void handleAfterUserCreate(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
    }

    /**
     * Deletes user info entity if user deleted.
     * @param user {@link User}
     */
    @HandleAfterDelete
    public void handleAfterUserDelete(User user) {
        userInfoRepository.deleteByUser(user);
    }

    @HandleBeforeSave
    public void handleUserInfo(UserInfo userInfo) throws AccessDeniedException {
        if(!securityService.isUsernameSameAsPrincipal(userInfo.getUser().getUsername()))
            throw new AccessDeniedException(STR . "Only user \{userInfo.getUser().getUsername()} can manage info about himself");
    }

    @HandleBeforeDelete
    public void handleUserInfoDelete(UserInfo userInfo) throws AccessDeniedException {
        throw new AccessDeniedException("Can't delete userInfo entity");
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    public void handleRoles(Role role) throws AccessDeniedException {
        throw new AccessDeniedException("Roles are pre-defined and can not be changed");
    }

    @HandleBeforeCreate
    @HandleBeforeDelete
    public void handleUserRoles(UserRole userRole) throws AccessDeniedException {
        if(!securityService.hasRole(RoleName.PROJECT_MANAGER))
            throw new AccessDeniedException("PROJECT_MANAGER role required");
    }
}
