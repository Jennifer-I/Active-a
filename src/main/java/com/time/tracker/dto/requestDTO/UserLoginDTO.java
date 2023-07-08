package com.time.tracker.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @Email(message = "Bros put better email joor")
    @NotEmpty(message = "try na mae e nor empty")
    private String email;
    @NotEmpty(message = "no password baba!")
    private String password;
}
