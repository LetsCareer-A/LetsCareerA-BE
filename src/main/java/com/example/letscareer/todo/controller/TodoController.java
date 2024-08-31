package com.example.letscareer.todo.controller;

import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessNonDataResponse;
import com.example.letscareer.common.dto.SuccessResponse;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.todo.dto.request.TodoRequest;
import com.example.letscareer.todo.dto.response.TodoResponse;
import com.example.letscareer.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;
    @GetMapping
    public ApiResponse getTodoList(
            @RequestHeader("userId") Long userId
    ){
        try {
            TodoResponse todoResponse = todoService.getTodos(userId);
            return SuccessResponse.success(SuccessCode.TODO_SUCCESS, todoResponse);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @PostMapping
    public ApiResponse saveTodo(
            @RequestHeader("userId") Long userId,
            @RequestBody TodoRequest request
            ){
        try {
            todoService.saveTodo(userId, request);
            return SuccessNonDataResponse.success(SuccessCode.TODO_SAVE_SUCCESS);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
}
