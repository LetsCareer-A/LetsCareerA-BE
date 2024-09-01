package com.example.letscareer.int_review.repository;

import com.example.letscareer.int_review.domain.IntReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface IntReviewRepository extends JpaRepository<IntReview, Long> {
    @Query("SELECT ir FROM IntReview ir JOIN ir.stage s WHERE ir.user.userId = :userId AND s.date BETWEEN :startDate AND :endDate")
    Page<IntReview> findAllByUserIdAndStageDeadlineWithin3Days(Long userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
