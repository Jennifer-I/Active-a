package com.time.tracker.controller;

import com.time.tracker.Enums.Status;
import com.time.tracker.dto.requestDTO.TaskRequest;
import com.time.tracker.dto.responseDTO.TaskCreatedResponseDTO;
import com.time.tracker.dto.responseDTO.TaskDeleteDTO;
import com.time.tracker.dto.responseDTO.TaskStatusResponseDTO;
import com.time.tracker.dto.responseDTO.TaskUpdatedDTO;
import com.time.tracker.exception.ApiResponse;
import com.time.tracker.services.IService.ITaskService;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new ApiResponse("Please login to be able to create a task");
        }

        TaskCreatedResponseDTO createdTask = taskService.createTask(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }


    @GetMapping("/all")
    public ResponseEntity<List<TaskCreatedResponseDTO>> viewAllTasks(HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new ApiResponse("logged in login to get all task");
        }

        List<TaskCreatedResponseDTO> tasks = taskService.getAllTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status")
    public ResponseEntity<List<TaskCreatedResponseDTO>> getTasksByStatus(
            @RequestParam("status") Status status,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new ApiResponse("Bros abeg try login before you can get this");
        }

        List<TaskCreatedResponseDTO> tasks = taskService.getTasksByStatus(status, userId);
        return ResponseEntity.ok(tasks);
    }



    @PatchMapping("/{taskId}")
    public ResponseEntity<?> editTask(@PathVariable Long taskId, @RequestBody TaskRequest updatedTask, HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new ApiResponse("User not logged in");
        }

        TaskUpdatedDTO task = taskService.updateTask(taskId, updatedTask, userId);
        return ResponseEntity.ok(task);
    }


    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new ApiResponse("Bros abeg try login");
        }

        TaskDeleteDTO response = taskService.deleteTask(taskId, userId);
        return ResponseEntity.status(HttpStatus.GONE).body(response);
    }

    @PostMapping("/status/{taskId}")
    public ResponseEntity<?> moveTaskStatus(@PathVariable Long taskId, HttpSession session, @RequestParam Status newStatus) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new ApiResponse("User not logged in");
        }

        TaskStatusResponseDTO task = taskService.moveTaskByStatus(newStatus, userId, taskId);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }



    @GetMapping("/{taskId}")
    public ResponseEntity<TaskCreatedResponseDTO> viewParticularTask(@PathVariable Long taskId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new ApiResponse("User not logged in");
        }

        TaskCreatedResponseDTO task = taskService.getTaskById(taskId, userId);
        return ResponseEntity.ok(task);
    }



}
