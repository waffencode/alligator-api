package com.alligator.alligatorapi.configuration.security.initialization;

import com.alligator.alligatorapi.model.entity.enums.RoleName;
import com.alligator.alligatorapi.model.entity.user.Role;
import com.alligator.alligatorapi.model.entity.user.User;
import com.alligator.alligatorapi.model.entity.user.UserRole;
import com.alligator.alligatorapi.model.repository.user.RoleRepository;
import com.alligator.alligatorapi.model.repository.user.UserRoleRepository;
import com.alligator.alligatorapi.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Creates user with name "admin" and role ROLE_ADMIN (see {@link RoleName}), if not exists
 */
@Component
@RequiredArgsConstructor
@DependsOn("rolesInitialization")
public class DefaultProjectManagerInitialization {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @PostConstruct
    public void init() {
        try {
            setTemporarySecurityContext();

            User admin = userService.exists("pm") ?
                    userService.loadFromDatabase("pm") :
                    userService.saveToDatabase(new User(null, "pm", "password"));

            Role roleAdmin = roleRepository.findByName(RoleName.PROJECT_MANAGER);

            if(!userRoleRepository.existsByUserAndRole(admin, roleAdmin)) {
                UserRole adminRole = new UserRole();
                adminRole.setUser(admin);
                adminRole.setRole(roleAdmin);
                userRoleRepository.save(adminRole);

            }

        } finally {
            clearSecurityContext();
        }
    }

    private void setTemporarySecurityContext() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("system", null, AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }
}
