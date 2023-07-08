package com.time.tracker.services.IService;

import com.time.tracker.dto.requestDTO.UserLoginDTO;
import com.time.tracker.dto.requestDTO.UserRequest;
import com.time.tracker.dto.requestDTO.UserRequestEdithDTO;
import com.time.tracker.dto.responseDTO.UserResponseDTO;

public interface IUserService {
    UserResponseDTO registerUser(UserRequest userRequestDTO);
    UserResponseDTO login(UserLoginDTO request);

    UserResponseDTO updateUser(UserRequestEdithDTO request, Long userId);

    String deleteUserById(Long userId);
}
