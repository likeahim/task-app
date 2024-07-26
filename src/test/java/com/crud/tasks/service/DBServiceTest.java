package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DBServiceTest {

    @InjectMocks
    DbService dbService;

    @Mock
    TaskRepository taskRepository;

    @Test
    void shouldFetchEmptyList() {
        //Given
        Task task = new Task(1L, "test_title", "test_content");
        List<Task> tasks = List.of(task);
        when(taskRepository.findAll()).thenReturn(new ArrayList<>());

        //When
        List<Task> allTasks = dbService.getAllTasks();

        //Then
        assertNotNull(allTasks);
        assertEquals(0, allTasks.size());
    }

    @Test
    void shouldThrowTaskNotFoundException() throws TaskNotFoundException {
        //Given
        Task task = new Task(2L, "test_title", "test_content");
        when(taskRepository.findById(2L)).thenReturn(Optional.of(new Task(3L, "false_title", "false_content")));

        //When
        Task returnedTask = dbService.getTask(2L);
        //Then
        assertNotNull(returnedTask);
        assertNotEquals(task.getId(), returnedTask.getId());
        assertNotEquals(task.getContent(), returnedTask.getContent());
    }

    @Test
    void shouldFetchSavedTask() {
        //Given
        Task task = new Task(5L, "saved_task", "saved_content");
        when(taskRepository.save(task)).thenReturn(task);

        //When
        Task savedTask = dbService.saveTask(task);

        //Then
        assertNotNull(savedTask);
        assertEquals(5L, savedTask.getId());
        assertEquals("saved_task", savedTask.getTitle());
    }
}
