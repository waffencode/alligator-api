package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.model.dto.response.TeamMemberUserInfoAndRolesResponse;
import com.alligator.alligatorapi.model.entity.team.Team;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.entity.user.UserInfo;
import com.alligator.alligatorapi.model.repository.team.TeamMemberRepository;
import com.alligator.alligatorapi.model.repository.team.TeamMemberRoleRepository;
import com.alligator.alligatorapi.model.repository.team.TeamRepository;
import com.alligator.alligatorapi.model.repository.user.UserInfoRepository;
import com.alligator.alligatorapi.model.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final UserInfoRepository userInfoRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberRoleRepository teamMemberRoleRepository;

    public List<TeamMemberUserInfoAndRolesResponse> getTeamMembersInfoAndRolesByTeamId(Long teamId) {
        List<TeamMemberUserInfoAndRolesResponse> responses = new ArrayList<>();

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team with ID " + teamId + " not found"));

        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeam(team);

        teamMembers.forEach(teamMember -> {
                    TeamMemberUserInfoAndRolesResponse response = new TeamMemberUserInfoAndRolesResponse();
                    // TODO: think about it
                    response.setUserInfo(userInfoRepository.findByUserId(teamMember.getUser().getId()).orElse(new UserInfo()));
                    response.setTeamMemberRoles(teamMemberRoleRepository.findAllByTeamMember(teamMember));

                    responses.add(response);
                });

        return responses;
    }
}
