package com.time.tracker.services;

import com.time.tracker.dto.requestDTO.UserLoginDTO;
import com.time.tracker.dto.requestDTO.UserRequest;
import com.time.tracker.dto.requestDTO.UserRequestEdithDTO;
import com.time.tracker.dto.responseDTO.LoginResponsDTO;
import com.time.tracker.dto.responseDTO.UserResponseDTO;
import com.time.tracker.entities.AppUser;
import com.time.tracker.exception.PasswordAndEmailNotFoundException;
import com.time.tracker.repository.UserRepository;
import com.time.tracker.services.IService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.time.tracker.exception.ApiResponse;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    @Autowired
    public AppUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO registerUser(UserRequest userRequestDTO) {
        Optional<AppUser> user = userRepository.findByEmail(userRequestDTO.getEmail());
        if (user.isPresent()) {
            throw new ApiResponse("Person don dey database Bro!", HttpStatus.CONFLICT);
        }

        AppUser newUser = AppUser.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();

        AppUser savedUser = userRepository.save(newUser);

        return UserResponseDTO.builder()
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public LoginResponsDTO login(UserLoginDTO request) {
        AppUser appUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new PasswordAndEmailNotFoundException("Person wey get the email nor dey our database bros",HttpStatus.NOT_FOUND));

        if (!request.getPassword().equals(appUser.getPassword())) {
            throw new PasswordAndEmailNotFoundException("Omo check your email or your password", HttpStatus.NOT_FOUND);
        }

        // Login successful
        LoginResponsDTO responseDTO = LoginResponsDTO.builder()
                .id(appUser.getId())
                .name(appUser.getName())
                .email(appUser.getEmail())
                .build();

        return responseDTO;
    }

    @Override
    public UserResponseDTO updateUser(UserRequestEdithDTO request, Long userId) {
        // Get the user using the userId
        Optional<AppUser> userOptional = userRepository.findById(userId);

        // Check if the user exists
        if (userOptional.isEmpty()) {
            throw new ApiResponse("User not found", HttpStatus.NOT_FOUND);
        }

        // Retrieve the user object from the Optional
        AppUser user = userOptional.get();

        // Update the UserRequestEdithDTO fields
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Save the updated user back to the database
        AppUser updatedUser = userRepository.save(user);

        // Build and return the response DTO
        UserResponseDTO responseDTO = UserResponseDTO.builder()
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .build();

        return responseDTO;
    }

    @Override
    public String deleteUserById(Long userId) {
        // Check if the user exists
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ApiResponse("User not found", HttpStatus.NOT_FOUND);
        }

        // Delete the user
        AppUser user = userOptional.get();
        userRepository.delete(user);

        return user.getName() + " Is deleted";
    }





}
