package com.example.letscareer.todo.repository;

import com.example.letscareer.schedule.domain.Schedule;
import com.example.letscareer.todo.domain.Todo;
import com.example.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUserUserId(Long userId);
    Optional<Todo> findByUserAndTodoId(User user,Long todoId);
    void deleteByTodoId(Long todoId);
}
