package com.example.letscareer.mid_review.repository;

import com.example.letscareer.mid_review.domain.MidReview;
import com.example.letscareer.stage.domain.Stage;
import com.example.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MidReviewRepository extends JpaRepository<MidReview, Long> {
    Boolean existsByStage(Stage stage);
    @Query("SELECT mr.midReviewId FROM MidReview mr WHERE mr.stage = :stage AND mr.user = :user")
    Optional<Long> findMidReviewIdByStageAndUser(Stage stage, User user);

    Optional<MidReview> findByMidReviewIdAndUser(Long midReviewId, User user);
}
