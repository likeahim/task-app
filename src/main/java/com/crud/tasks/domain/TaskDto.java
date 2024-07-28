package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(id, taskDto.id) && Objects.equals(title, taskDto.title) && Objects.equals(content, taskDto.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content);
    }
}
