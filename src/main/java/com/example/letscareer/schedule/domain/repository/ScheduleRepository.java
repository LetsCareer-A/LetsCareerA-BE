package com.example.letscareer.schedule.domain.repository;

import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Page<Schedule> findAllByUserUserIdAndAlwaysTrue(Long userId, Pageable pageable);
    Page<Schedule> findAllByUserUserId(Long userId, Pageable pageable);
    List<Schedule> findAllByUserUserId(Long userId);
    Optional<Schedule> findByUserAndScheduleId(User user, Long scheduleId);

    @Query("SELECT DISTINCT s.company FROM Schedule s WHERE s.user = :user")
    Page<String> findDistinctCompanyByUser(User user, Pageable pageable);
    List<Schedule> findAllByUserAndCompany(User user, String company);

}
