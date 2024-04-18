package com.alligator.alligatorapi.model.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    public String username;
    public String password;
}
