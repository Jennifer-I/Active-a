package com.time.tracker.dto.requestDTO;

import com.time.tracker.Enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskStatusDTO {
    private Long taskId;
    private Long userId;
    private Status status;
}
