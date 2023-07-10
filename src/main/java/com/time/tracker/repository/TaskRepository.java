package com.time.tracker.repository;

import com.time.tracker.entities.AppUser;
import com.time.tracker.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task>findByAppUser(AppUser user);
}
