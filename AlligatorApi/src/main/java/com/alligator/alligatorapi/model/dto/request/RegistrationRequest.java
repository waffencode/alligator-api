package com.alligator.alligatorapi.model.dto.request;

import lombok.Data;

@Data
public class RegistrationRequest {
    public String username;
    public String password;
    public String email;
    public String fullName;
    public String phoneNumber;
}
