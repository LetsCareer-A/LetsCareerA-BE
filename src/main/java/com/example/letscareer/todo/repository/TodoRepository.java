package com.example.letscareer.todo.repository;

import com.example.letscareer.schedule.domain.Schedule;
import com.example.letscareer.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Schedule, Long> {
    List<Todo> findAllByUserId(Long userId);
}
