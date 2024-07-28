package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper mapper;

    @Test
    void shouldFetchListWithTasks() throws Exception {
        //Given
        List<Task> tasks = List.of(
                new Task(1L, "name task 1", "content task 1"),
                new Task(2L, "name task 2", "content task 2")
        );
        List<TaskDto> tasksDtos = List.of(
                new TaskDto(1L, "name task 1", "content task 1"),
                new TaskDto(2L, "name task 2", "content task 2")
        );
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(mapper.mapToTaskDtoList(tasks)).thenReturn(tasksDtos);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.hasToString("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("name task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("content task 2")));
    }

    @Test
    void shouldFetchTaskById() throws Exception {
        //Given
        Task task = new Task(1L, "name task 1", "content task 1");
        TaskDto taskDto = new TaskDto(1L, "name task 1", "content task 1");
        when(dbService.getTask(task.getId())).thenReturn(task);
        when(mapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("name task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("content task 1")));
    }

    @Test
    void shouldDeleteTaskById() throws Exception {
        //Given
        Task task = new Task(1L, "name task 1", "content task 1");
        doNothing().when(dbService).deleteTask(task);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(1L, "name task", "content task");
        Task updatedTask = new Task(1L, "updated name task", "updated content task");
        TaskDto taskDto = new TaskDto(1L, "updated name task", "updated content task");
        when(mapper.mapToTask(taskDto)).thenReturn(updatedTask);
        when(dbService.saveTask(updatedTask)).thenReturn(updatedTask);
        when(mapper.mapToTaskDto(updatedTask)).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonCont = gson.toJson(taskDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonCont))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("updated name task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("updated content task")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "name task", "content task");
        Task task = new Task(1L, "name task", "content task");
        when(mapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        Gson gson = new Gson();
        String jsonCont = gson.toJson(taskDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonCont))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}