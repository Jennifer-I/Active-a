package com.time.tracker.controller;

import com.time.tracker.dto.requestDTO.UserLoginDTO;
import com.time.tracker.dto.requestDTO.UserRequest;
import com.time.tracker.dto.requestDTO.UserRequestEdithDTO;
import com.time.tracker.dto.responseDTO.UserResponseDTO;
import com.time.tracker.services.IService.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequest request) {
        var response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody @Valid UserLoginDTO loginRequest) {
        var  authenticatedUser = userService.login(loginRequest);
        return ResponseEntity.ok(authenticatedUser);
    }
    @PutMapping("/edit/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserRequestEdithDTO request
    ) {
        UserResponseDTO updatedUser = userService.updateUser(request, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        String Message = userService.deleteUserById(userId);
        return ResponseEntity.ok(Message);
    }

}
