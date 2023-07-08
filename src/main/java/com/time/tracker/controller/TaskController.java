package com.time.tracker.controller;

import com.time.tracker.Enums.Status;
import com.time.tracker.dto.requestDTO.TaskDeleteRequestDTO;
import com.time.tracker.dto.requestDTO.TaskRequest;
import com.time.tracker.dto.requestDTO.TaskStatusDTO;
import com.time.tracker.dto.responseDTO.TaskCreatedResponseDTO;
import com.time.tracker.dto.responseDTO.TaskDeleteDTO;
import com.time.tracker.dto.responseDTO.TaskStatusResponseDTO;
import com.time.tracker.dto.responseDTO.TaskUpdatedDTO;
import com.time.tracker.services.IService.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final ITaskService taskService;
    @Autowired
    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create/{appUser_id}")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest request, @PathVariable("appUser_id") Long appUser_id) {
        TaskCreatedResponseDTO createdTask = taskService.createTask(request, appUser_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }


    @GetMapping("/all/{userid}")
    public ResponseEntity<List<TaskCreatedResponseDTO>> getAllTasks(@PathVariable("userid") Long userid) {
        List<TaskCreatedResponseDTO> tasks = taskService.getAllTasks(userid);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status")
    public ResponseEntity<List<?>> getTasksByStatus(@RequestBody TaskStatusDTO request) {
        List<TaskCreatedResponseDTO> tasks = taskService.getTasksByStatus(request);
        return ResponseEntity.ok(tasks);
    }

    @PatchMapping ("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody TaskRequest updatedTask) {
        TaskUpdatedDTO task = taskService.updateTask(taskId, updatedTask);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/delete/{userId}/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, @PathVariable Long userId) {
        TaskDeleteDTO response = taskService.deleteTask(taskId, userId);
        return ResponseEntity.status(HttpStatus.GONE).body(response);
    }

    @PostMapping("/statusS/{userId}/{taskId}")
    public ResponseEntity<?> moveTaskStatus(
            @PathVariable Long taskId,
            @PathVariable Long userId,
            @RequestParam Status newStatus
    ) {
        TaskStatusResponseDTO task = taskService.moveTaskStatus(newStatus, userId, taskId);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskCreatedResponseDTO> getTaskById(@PathVariable Long taskId) {
        TaskCreatedResponseDTO task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }


}
