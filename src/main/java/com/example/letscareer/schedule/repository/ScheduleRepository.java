package com.example.letscareer.schedule.repository;

import com.example.letscareer.schedule.domain.Schedule;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.user.userId = :userId AND FUNCTION('MONTH', s.date) = :month")
    Page<Schedule> findAllByUserIdAndMonth(@Param("userId") Long userId, @Param("month") int month, Pageable pageable);
}
