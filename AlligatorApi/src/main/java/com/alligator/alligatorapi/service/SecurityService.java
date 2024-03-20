package com.alligator.alligatorapi.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class SecurityService {
    public Boolean validateUsernameSameAsPrincipal(String name) throws AccessDeniedException {
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!name.equals(principalName))
            throw new AccessDeniedException("Name " + name + " failed validation for rights check on this method");

        return true;
    }
}
