package com.example.letscareer.todo.dto.response;

import com.example.letscareer.todo.dto.TodoDTO;

import java.util.List;

public record TodoResponse(
        List<TodoDTO> todos
) {
}
