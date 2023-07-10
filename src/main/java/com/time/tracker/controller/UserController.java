package com.time.tracker.controller;

import com.time.tracker.dto.requestDTO.UserLoginDTO;
import com.time.tracker.dto.requestDTO.UserRequest;
import com.time.tracker.dto.requestDTO.UserRequestEdithDTO;
import com.time.tracker.dto.responseDTO.UserResponseDTO;
import com.time.tracker.exception.myOwnException;
import com.time.tracker.services.IService.IUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final IUserService userService;
    private final HttpSession session;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequest request) {
        var response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid UserLoginDTO loginRequest) {
        var  authenticatedUser = userService.login(loginRequest);
        session.setAttribute("userid", authenticatedUser.getId());
        return ResponseEntity.ok(authenticatedUser);
    }
    @PutMapping("/edit")
    public ResponseEntity<UserResponseDTO> editUserInformation(
            @RequestBody @Valid UserRequestEdithDTO request,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new myOwnException("You never log in ooo");
        }

        UserResponseDTO updatedUser = userService.updateUser(request, userId);
        return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        String Message = userService.deleteUserById(userId);
        return ResponseEntity.ok(Message);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        // Invalidate the user's session
        session.invalidate();

        return ResponseEntity.ok("Logout successful");
    }


}
