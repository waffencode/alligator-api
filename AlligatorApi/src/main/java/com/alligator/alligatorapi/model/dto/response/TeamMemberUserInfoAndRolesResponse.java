package com.alligator.alligatorapi.model.dto.response;

import com.alligator.alligatorapi.model.entity.team.TeamMemberRole;
import com.alligator.alligatorapi.model.entity.team.TeamRole;
import com.alligator.alligatorapi.model.entity.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberUserInfoAndRolesResponse {
    private Long teamMemberId;
    private UserInfo userInfo;
    private List<TeamMemberRole> teamMemberRoles;
}
