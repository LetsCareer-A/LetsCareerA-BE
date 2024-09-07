package com.example.letscareer.stage.domain.repository;

import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.stage.domain.model.Stage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface StageRepositoryCustom {
    Page<Stage> findAllByUserIdAndMonth(Long userId, int month, Pageable pageable);
    List<Stage> findAllByUserIdAndMonth(@Param("userId") Long userId, @Param("month") int month);
    List<Stage> findAllByUserIdAndDate(Long userId, LocalDate date);
    Page<Stage> findAllByScheduleInAndDateBetweenAndIntReviewNotExistsAndMidReviewNotExists(List<Schedule> schedules, LocalDate threeDaysPrevious, LocalDate today, Pageable pageable);
}