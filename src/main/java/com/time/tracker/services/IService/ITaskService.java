package com.time.tracker.services.IService;
import com.time.tracker.Enums.Status;
import com.time.tracker.dto.requestDTO.TaskDeleteRequestDTO;
import com.time.tracker.dto.requestDTO.TaskRequest;
import com.time.tracker.dto.requestDTO.TaskStatusDTO;
import com.time.tracker.dto.responseDTO.TaskCreatedResponseDTO;
import com.time.tracker.dto.responseDTO.TaskDeleteDTO;
import com.time.tracker.dto.responseDTO.TaskStatusResponseDTO;
import com.time.tracker.dto.responseDTO.TaskUpdatedDTO;

import java.util.List;

public interface ITaskService {
    TaskCreatedResponseDTO createTask(TaskRequest request, Long userid);
    List<TaskCreatedResponseDTO> getAllTasks(Long userId);
    List<TaskCreatedResponseDTO> getTasksByStatus(TaskStatusDTO request);
    TaskUpdatedDTO updateTask(Long taskId, TaskRequest request);
    TaskDeleteDTO deleteTask(TaskDeleteRequestDTO requestDTO);
    TaskStatusResponseDTO moveTaskStatus(Status newStatus, Long userId, Long taskId);
    TaskCreatedResponseDTO getTaskById(Long taskId);
}
