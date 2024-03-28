package com.alligator.alligatorapi.entity.sprint;

import com.alligator.alligatorapi.entity.team.TeamRole;
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
@Table(name = "sprint_task_roles")
public class SprintTaskRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private SprintTask task;
    @ManyToOne
    @JoinColumn(name = "team_role_id")
    private TeamRole role;
}
