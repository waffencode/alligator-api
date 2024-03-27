package com.alligator.alligatorapi.entity.team;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "team_member_roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"team_member_id", "team_role_id"})
})
public class TeamMemberRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    @ManyToOne
    @JoinColumn(name = "team_role_id")
    private TeamRole role;
}
