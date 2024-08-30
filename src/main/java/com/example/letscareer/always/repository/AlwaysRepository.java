package com.example.letscareer.always.repository;

import com.example.letscareer.always.domain.Always;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlwaysRepository extends JpaRepository<Always, Long> {
    Page<Always> findAllByUserUserId(Long userId, Pageable pageable);
}
