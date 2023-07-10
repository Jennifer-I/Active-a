package com.time.tracker.services.IService;
import com.time.tracker.Enums.Status;
import com.time.tracker.dto.requestDTO.TaskRequest;
import com.time.tracker.dto.responseDTO.TaskCreatedResponseDTO;
import com.time.tracker.dto.responseDTO.TaskDeleteDTO;
import com.time.tracker.dto.responseDTO.TaskStatusResponseDTO;
import com.time.tracker.dto.responseDTO.TaskUpdatedDTO;

import java.util.List;

public interface ITaskService {
    TaskCreatedResponseDTO createTask(TaskRequest request, Long userid);
    List<TaskCreatedResponseDTO> getAllTasks(Long userId);
    List<TaskCreatedResponseDTO> getTasksByStatus(Status status, Long userId);
    TaskUpdatedDTO updateTask(Long taskId, TaskRequest request, Long userId);
    TaskDeleteDTO deleteTask(Long taskId, Long userId);
    TaskStatusResponseDTO moveTaskByStatus(Status newStatus, Long userId, Long taskId);
    TaskCreatedResponseDTO getTaskById(Long taskId, Long userId);
}
