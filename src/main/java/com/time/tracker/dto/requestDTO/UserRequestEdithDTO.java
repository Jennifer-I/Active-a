package com.time.tracker.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequestEdithDTO {
    @NotBlank(message = "please provide your name")
    private String name;
    @Email(message = "put better email na Bros!")
    @NotBlank(message = "You forget to put email Bros!")
    private String email;
}
