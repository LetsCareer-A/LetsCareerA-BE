package com.example.letscareer.int_review.repository;

import com.example.letscareer.int_review.domain.IntReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntReviewRepository extends JpaRepository<IntReview, Long> {
}
