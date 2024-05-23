package com.alligator.alligatorapi.controller;

import com.alligator.alligatorapi.model.entity.user.UserInfo;
import com.alligator.alligatorapi.model.repository.team.TeamMemberRepository;
import com.alligator.alligatorapi.model.repository.team.TeamMemberRoleRepository;
import com.alligator.alligatorapi.model.repository.team.TeamRepository;
import com.alligator.alligatorapi.model.repository.user.UserInfoRepository;
import com.alligator.alligatorapi.model.repository.user.UserRepository;
import com.alligator.alligatorapi.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class TeamController {
    private final TeamService teamService;

    @GetMapping(path = "/teams/{teamId}/getTeamMembersInfoAndRoles")
    public ResponseEntity<?> getTeamMembersInfoAndRoles(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.getTeamMembersInfoAndRolesByTeamId(teamId));
    }
}
