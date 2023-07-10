package com.time.tracker.entities;
import com.time.tracker.Enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
@Table(
        name = "task",
        uniqueConstraints = @UniqueConstraint(columnNames = "id"),
        indexes = @Index(columnList = "status")
)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer allocatedTime;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;


    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "appUser_id")
    private AppUser appUser;

    public Task(long l, String title, String description, Status status, int i, AppUser user) {
    }
}

