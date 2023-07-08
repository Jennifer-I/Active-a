package com.time.tracker.dto.responseDTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
}
