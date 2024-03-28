package com.alligator.alligatorapi.entity.sprint;

import com.alligator.alligatorapi.entity.enums.TaskSwapRequestState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "task_swap_requests")
public class TaskSwapRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_1_id")
    private SprintTask task1;
    @ManyToOne
    @JoinColumn(name = "task_2_id")
    private SprintTask task2;

    private Timestamp requestTimestamp;

    @Enumerated(EnumType.STRING)
    private TaskSwapRequestState state;

}
