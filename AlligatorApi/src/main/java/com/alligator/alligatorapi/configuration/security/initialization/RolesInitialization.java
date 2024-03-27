package com.alligator.alligatorapi.configuration.security.initialization;

import com.alligator.alligatorapi.entity.enums.RoleName;
import com.alligator.alligatorapi.entity.user.Role;
import com.alligator.alligatorapi.repository.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Iterates over {@link RoleName} and creates roles, if not exists
 */
@Component
@RequiredArgsConstructor
public class RolesInitialization {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        for (RoleName roleName : RoleName.values()) {
            boolean roleExists = roleRepository.existsByName(roleName);
            if (!roleExists) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }
}
