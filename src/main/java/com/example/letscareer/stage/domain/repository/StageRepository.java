package com.example.letscareer.stage.domain.repository;

import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.stage.domain.model.Stage;
import com.example.letscareer.stage.domain.model.Type;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long>, StageRepositoryCustom {
    Page<Stage> findAllByUserIdAndMonth(@Param("userId") Long userId, @Param("month") int month, Pageable pageable);
    List<Stage> findAllByUserIdAndMonth(@Param("userId") Long userId, @Param("month") int month);
    List<Stage>findAllByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
    Optional<Stage> findTopByScheduleScheduleIdOrderByOrderDesc(Long scheudleId);
    Page<Stage> findAllByScheduleInAndDateBetweenAndIntReviewNotExistsAndMidReviewNotExists(List<Schedule> schedules, LocalDate threeDaysPrevious, LocalDate today, Pageable pageable);
    Optional<Stage> findByStageIdAndSchedule(Long stageId, Schedule schedule);
    List<Stage> findAllBySchedule(Schedule schedule);

    List<Stage> findBySchedule(Schedule schedule);

    Optional<Stage> findByScheduleAndStageIdAndType(Schedule schedule, Long stageId, Type type);
}
