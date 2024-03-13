package com.alligator.alligatorapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernameAuthoritiesResponse {
    public Long id;
    public String username;
    public String authorities;
}
