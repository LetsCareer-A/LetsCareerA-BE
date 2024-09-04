package com.example.letscareer.todo.domain.dto.response;

import com.example.letscareer.todo.domain.dto.TodoDTO;

import java.util.List;

public record TodoResponse(
        List<TodoDTO> todos
) {
}
