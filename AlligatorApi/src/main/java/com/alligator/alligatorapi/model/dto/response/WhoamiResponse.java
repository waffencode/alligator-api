package com.alligator.alligatorapi.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhoamiResponse {
    private Long id;
    private String username;
    private List<String> roles;
}
