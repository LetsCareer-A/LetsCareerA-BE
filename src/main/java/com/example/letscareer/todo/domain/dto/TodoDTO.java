package com.example.letscareer.todo.domain.dto;

public record TodoDTO(
        Long todoId,
        String todo,
        Boolean isChecked
) {
}
