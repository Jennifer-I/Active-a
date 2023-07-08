package com.time.tracker.dto.requestDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDeleteRequestDTO {
    private Long taskId;
    private Long userId;

}
