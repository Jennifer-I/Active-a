package com.time.tracker.dto.responseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponsDTO {
    private Long id;
    private String name;
    private String email;
}
