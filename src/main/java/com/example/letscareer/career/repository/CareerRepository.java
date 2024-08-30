package com.example.letscareer.career.repository;

import com.example.letscareer.career.domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
}
