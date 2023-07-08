package com.time.tracker.dto.responseDTO;

import com.time.tracker.Enums.Status;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
public class TaskUpdatedDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;
    @Temporal(
            value = TemporalType.TIMESTAMP
    )
    private LocalDateTime updated;
}
