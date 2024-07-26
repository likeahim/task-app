package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskMapperTest {

    @Autowired
    TaskMapper taskMapper;

    @Test
    void shouldMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test_title", "test_content");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals("test_title", task.getTitle());
        assertEquals(1L, task.getId());
    }

    @Test
    void shouldMapToTaskDto() {
        //Given
        Task task = new Task(1L, "test_title", "test_content");
        TaskDto taskDto = new TaskDto(1L, "test_title", "test_content");

        //When
        TaskDto returnedTaskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertNotNull(returnedTaskDto);
        assertNotEquals("false_title", returnedTaskDto.getTitle());
        assertEquals("test_content", returnedTaskDto.getContent());
    }

    @Test
    void shouldFetchMappedList() {
        //Given
        Task task1 = new Task(10L, "first_task_title", "first_task_content");
        Task task2 = new Task(11L, "second_task_title", "second_task_content");
        List<Task> tasks = List.of(task1, task2);

        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        //Then
        assertNotNull(taskDtos);
        assertEquals(2, taskDtos.size());
        assertEquals(10L, taskDtos.get(0).getId());
    }
}