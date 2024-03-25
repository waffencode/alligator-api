package com.alligator.alligatorapi.repository.user;

import com.alligator.alligatorapi.entity.user.Role;
import com.alligator.alligatorapi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


// TODO: переделать модель на передфайнд роли + список ролей юзеров вместо сырого ролевого использования
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByUser(User user);
}