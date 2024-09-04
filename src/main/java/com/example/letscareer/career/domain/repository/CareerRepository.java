package com.example.letscareer.career.domain.repository;

import com.example.letscareer.career.domain.model.Career;
import com.example.letscareer.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    public Optional<Career> findByCareerIdAndUser(Long careerId, User user);

    public Page<Career> findByUser(User user, Pageable pageable);
}
