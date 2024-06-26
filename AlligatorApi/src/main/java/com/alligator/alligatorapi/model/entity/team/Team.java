package com.alligator.alligatorapi.model.entity.team;

import com.alligator.alligatorapi.model.entity.enums.TeamState;
import com.alligator.alligatorapi.model.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_lead_id")
    private User teamLead;

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TeamState state;
}
