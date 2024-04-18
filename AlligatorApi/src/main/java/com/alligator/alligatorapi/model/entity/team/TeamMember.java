package com.alligator.alligatorapi.model.entity.team;

import com.alligator.alligatorapi.model.entity.enums.TeamMemberState;
import com.alligator.alligatorapi.model.entity.user.User;
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
@Table(name = "team_members", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"team_id", "user_id"})
})
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private TeamMemberState state;
}
