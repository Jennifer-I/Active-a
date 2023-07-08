package com.time.tracker.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "please provide your name")
    private String name;
    @Email(message = "put better email na Bros!")
    @NotBlank(message = "You forget to put email Bros!")
    private String email;
    @NotBlank(message = "Omo you no go put better password")
    private String password;
}
