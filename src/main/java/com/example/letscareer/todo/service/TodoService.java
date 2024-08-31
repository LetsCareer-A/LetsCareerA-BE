package com.example.letscareer.todo.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.todo.domain.Todo;
import com.example.letscareer.todo.dto.TodoDTO;
import com.example.letscareer.todo.dto.response.TodoResponse;
import com.example.letscareer.todo.repository.TodoRepository;
import com.example.letscareer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.letscareer.common.exception.enums.ErrorCode.USER_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    public TodoResponse getTodos(final Long userId) {
        List<Todo> todoList = todoRepository.findAllByUserId(userId);
        if (todoList.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND_EXCEPTION);
        }

        List<TodoDTO> todos = todoList.stream()
                .map(t -> new TodoDTO(t.getContent(), t.isChecked()))
                .collect(Collectors.toList());

        return new TodoResponse(todos);
    }
}
