package com.example.letscareer.todo.service;

import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.todo.domain.model.Todo;
import com.example.letscareer.todo.domain.dto.TodoDTO;
import com.example.letscareer.todo.domain.dto.request.TodoRequest;
import com.example.letscareer.todo.domain.dto.response.TodoResponse;
import com.example.letscareer.todo.domain.repository.TodoRepository;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.letscareer.common.exception.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    public TodoResponse getTodos(final Long userId) {
        List<Todo> todoList = todoRepository.findAllByUserUserId(userId);

        List<TodoDTO> todos = todoList.stream()
                .map(t -> new TodoDTO(t.getTodoId(),t.getContent(), t.isChecked()))
                .collect(Collectors.toList());

        return new TodoResponse(todos);
    }

    @Transactional
    public void saveTodo(final Long userId, final TodoRequest request){
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        Todo newTodo = Todo.toEntity(user, request);
        todoRepository.save(newTodo);
    }
    @Transactional
    public void deleteTodo(final Long userId, final Long todoId) {
        Todo todo = todoRepository.findByUserUserIdAndTodoId(userId, todoId)
                .orElseThrow(() -> new NotFoundException(TODO_NOT_FOUND_EXCEPTION));

        todoRepository.delete(todo);
    }

    @Transactional
    public void changeTodoChecked(Long userId, Long todoId) {
        Todo todo = todoRepository.findByUserUserIdAndTodoId(userId, todoId)
                .orElseThrow(() -> new NotFoundException(TODO_NOT_FOUND_EXCEPTION));

        // Toggle the isChecked field
        todo.toggleChecked();

        todoRepository.save(todo);
    }
}
