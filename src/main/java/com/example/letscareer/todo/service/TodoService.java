package com.example.letscareer.todo.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.todo.domain.Todo;
import com.example.letscareer.todo.dto.TodoDTO;
import com.example.letscareer.todo.dto.request.TodoRequest;
import com.example.letscareer.todo.dto.response.TodoResponse;
import com.example.letscareer.todo.repository.TodoRepository;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import static com.example.letscareer.common.exception.enums.ErrorCode.TODO_NOT_FOUND_EXCEPTION;
import static com.example.letscareer.common.exception.enums.ErrorCode.USER_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    public TodoResponse getTodos(final Long userId) {
        List<Todo> todoList = todoRepository.findAllByUserUserId(userId);

        List<TodoDTO> todos = todoList.stream()
                .map(t -> new TodoDTO(t.getContent(), t.isChecked()))
                .collect(Collectors.toList());

        return new TodoResponse(todos);
    }
    public void saveTodo(final Long userId, final TodoRequest request){
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        Todo newTodo = Todo.builder()
                .content(request.todo())
                .isChecked(false)
                .user(user)
                .build();
        todoRepository.save(newTodo);
    }
    @Transactional
    public void deleteTodo(final Long userId, final Long todoId){
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        Optional<Todo> todo = todoRepository.findByUserAndTodoId(user, todoId);
        if(todo.isEmpty()){
            throw new NotFoundException(TODO_NOT_FOUND_EXCEPTION);
        }else{
            todoRepository.deleteByTodoId(todo.get().getTodoId());
        }
    }
}
