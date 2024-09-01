package com.example.letscareer.mid_review.repository;

import com.example.letscareer.mid_review.domain.MidReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MidReviewRepository extends JpaRepository<MidReview, Long> {
}