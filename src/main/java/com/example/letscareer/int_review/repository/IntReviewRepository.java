package com.example.letscareer.int_review.repository;

import com.example.letscareer.int_review.domain.IntReview;
import com.example.letscareer.stage.domain.Stage;
import com.example.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntReviewRepository extends JpaRepository<IntReview, Long> {
    Boolean existsByStage(Stage stage);
    @Query("SELECT ir.intReviewId FROM IntReview ir WHERE ir.stage = :stage AND ir.user = :user")
    Optional<Long> findIntReviewIdByStageAndUser(Stage stage, User user);
    Optional<IntReview> findByIntReviewIdAndUser(Long intReviewId, User user);
}
