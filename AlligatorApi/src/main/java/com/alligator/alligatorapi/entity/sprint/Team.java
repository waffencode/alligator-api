package com.alligator.alligatorapi.entity.sprint;

import com.alligator.alligatorapi.entity.enums.TeamState;
import com.alligator.alligatorapi.entity.user.User;
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
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_lead_id")
    private User teamLead;

    private String name;

    @Enumerated(EnumType.STRING)
    private TeamState state;
}
