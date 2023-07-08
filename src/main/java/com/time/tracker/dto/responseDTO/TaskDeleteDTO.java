package com.time.tracker.dto.responseDTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskDeleteDTO {
    private Long id;
    private String message;
}
