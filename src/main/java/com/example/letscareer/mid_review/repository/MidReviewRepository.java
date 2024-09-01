package com.example.letscareer.mid_review.repository;

import com.example.letscareer.mid_review.domain.MidReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface MidReviewRepository extends JpaRepository<MidReview, Long> {
    @Query("SELECT mr FROM MidReview mr JOIN mr.stage s WHERE mr.user.userId = :userId AND s.date BETWEEN :startDate AND :endDate")
    Page<MidReview> findAllByUserIdAndStageDeadlineWithin3Days(Long userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
