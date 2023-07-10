package com.time.tracker.dto.responseDTO;

import com.time.tracker.Enums.Status;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class TaskStatusResponseDTO {
    private String title;
    private String description;
    private Status status;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;


}
