package com.time.tracker.repository;

import com.time.tracker.Enums.Status;
import com.time.tracker.entities.AppUser;
import com.time.tracker.entities.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest    //spring will only start what is required for test
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;
    //save -- given/when/then
    @Test
    public void whenGivenWhenSavedThenReturnObject(){
        AppUser user = new AppUser();
        //given
        Task task = new Task(1L, "make money", "go to work", Status.PENDING,12,user);
        //when
        Task actual = repository.save(task);
        //then
        assertNotNull(actual);
    }
    //read
    @Test
    public void whenGivenId1WhenFindByIdThenReturn()
    {
        //given
        Long id1 = 1L;
        //when

        AppUser user = new AppUser();
        Task task = new Task(1L,"make money", "go to work", Status.PENDING,12,user);
        //when
        repository.save(task);
        Optional<Task> optionalTask = repository.findById(id1);
        //then
        assertNotNull(optionalTask.get());
//        Long expected = task.getId();
//        Long actual = 1L;
//        assertEquals(expected, actual);

    }
    @BeforeEach
    void setUp() {
    }

    @Test
    void findByAppUser() {
    }
}