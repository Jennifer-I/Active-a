package com.time.tracker.services;

import com.time.tracker.Enums.Status;
import com.time.tracker.dto.requestDTO.TaskRequest;
import com.time.tracker.dto.responseDTO.TaskCreatedResponseDTO;
import com.time.tracker.dto.responseDTO.TaskDeleteDTO;
import com.time.tracker.dto.responseDTO.TaskStatusResponseDTO;
import com.time.tracker.dto.responseDTO.TaskUpdatedDTO;
import com.time.tracker.entities.AppUser;
import com.time.tracker.entities.Task;
import com.time.tracker.exception.myOwnException;
import com.time.tracker.repository.TaskRepository;
import com.time.tracker.repository.UserRepository;
import com.time.tracker.services.IService.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskCreatedResponseDTO createTask(TaskRequest request, Long userid) {
        Optional<AppUser> optionalAppUser = userRepository.findById(userid);
        if (optionalAppUser.isEmpty()) {
            throw new myOwnException("Omo the person nor dey ooh");
        }

        AppUser appUser = optionalAppUser.get();

        // Create a new Task object and set its properties
        Task newTask = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(Status.PENDING) // Set the default status to PENDING
                .createdAt(LocalDateTime.now())
                .appUser(appUser)
                .build();

        // Save the new task in the database
        Task savedTask = taskRepository.save(newTask);

        // Create a response DTO with the ID of the created task
        TaskCreatedResponseDTO responseDTO = TaskCreatedResponseDTO.builder()
                .id(savedTask.getId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .status(savedTask.getStatus())
                .createdAt(savedTask.getCreatedAt())
                .build();

        return responseDTO;
    }


    @Override
    public List<TaskCreatedResponseDTO> getAllTasks(Long userId) {
        // Find the user by ID
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new myOwnException("User not found", HttpStatus.NOT_FOUND);
        }

        AppUser user = userOptional.get();

        // Retrieve all tasks created by the user
        List<Task> tasks = taskRepository.findByAppUser(user);

        // Map the tasks to TaskCreatedResponseDTO and return
        return tasks.stream()
                .map(task -> TaskCreatedResponseDTO.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .status(task.getStatus())
                        .createdAt(task.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskCreatedResponseDTO> getTasksByStatus(Status status, Long userId) {
        // Get all tasks for the specified user
        List<TaskCreatedResponseDTO> allTasks = getAllTasks(userId);
        // Filter tasks by the specified status and Return the filtered tasks
        return allTasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public TaskUpdatedDTO updateTask(Long taskId, TaskRequest request, Long userId) {
        // Retrieve the task to be updated from the database
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            throw new myOwnException("Omo the task nor dey oo", HttpStatus.NO_CONTENT);
        }

        Task existingTask = optionalTask.get();

        // Check if the task belongs to the specified user
        if (!existingTask.getAppUser().getId().equals(userId)) {
            throw new myOwnException("Access denied. Task does not belong to the user");
        }

        // Update the task properties with the values from the request
        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setUpdatedAt(LocalDateTime.now());

        // Save the updated task in the database
        Task updatedTask = taskRepository.save(existingTask);

        // Create a response DTO with the updated task details
        return TaskUpdatedDTO.builder()
                .id(updatedTask.getId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .status(updatedTask.getStatus())
                .updated(updatedTask.getUpdatedAt())
                .build();
    }


    @Override
    public TaskDeleteDTO deleteTask(Long taskId, Long userId) {
        //find the task by the Id
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new myOwnException("task nor dey", HttpStatus.NO_CONTENT);
        }
        //find the user using the giving id
        Optional<AppUser> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new myOwnException("the user not in the database");
        }
        //get both user and task
        Task existingTask = task.get();
        AppUser existingUser = user.get();
        //check if the user is the user that created the task
        if (!existingTask.getAppUser().getId().equals(existingUser.getId())) {
            throw new myOwnException("You are not eligible to delete the task", HttpStatus.FORBIDDEN);
        }
        //delete the task
        taskRepository.delete(existingTask);
        return TaskDeleteDTO.builder()
                .id(existingTask.getId()).message(" Is Successfully deleted").build();
    }
    @Override
    public TaskStatusResponseDTO moveTaskByStatus(Status newStatus, Long userId, Long taskId) {
        // Get the user
        Optional<AppUser> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new myOwnException("User not found", HttpStatus.NOT_FOUND);
        }
        AppUser user1 = user.get();

        // Get the task
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new myOwnException("The task nor dey", HttpStatus.NOT_FOUND);
        }
        Task existingTask = task.get();

        // Check if the user is authorized to change the status
        if (!existingTask.getAppUser().getId().equals(user1.getId())) {
            throw new myOwnException("Not authorized to change status", HttpStatus.FORBIDDEN);
        }

        // Update the task status
        existingTask.setStatus(newStatus);

        // Update completedAt timestamp if the newStatus is DONE
        if (newStatus == Status.DONE) {
            existingTask.setCompletedAt(LocalDateTime.now());
        }
        else{
            existingTask.setUpdatedAt(LocalDateTime.now());
        }

        // Save the updated task
        taskRepository.save(existingTask);

        // Build and return the response DTO
        return TaskStatusResponseDTO.builder()
                .title(existingTask.getTitle())
                .description(existingTask.getDescription())
                .status(existingTask.getStatus())
                .updatedAt(existingTask.getUpdatedAt())
                .completedAt(existingTask.getCompletedAt())
                .build();
    }



    @Override
    public TaskCreatedResponseDTO getTaskById(Long taskId, Long userId) {
        // Get the task by the taskId using Optional
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        // Check if the task exists
        if (taskOptional.isEmpty()) {
            throw new myOwnException("Task not found", HttpStatus.NOT_FOUND);
        }

        // Retrieve the task object from the Optional
        Task task = taskOptional.get();

        // Check if the task belongs to the specified user
        if (!task.getAppUser().getId().equals(userId)) {
            throw new myOwnException("Access denied. Task does not belong to YOU",HttpStatus.FORBIDDEN);
        }

        // Create and return the response DTO
        TaskCreatedResponseDTO responseDTO = TaskCreatedResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .build();

        return responseDTO;
    }



}




