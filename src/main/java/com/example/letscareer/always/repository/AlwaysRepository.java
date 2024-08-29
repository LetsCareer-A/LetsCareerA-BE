package com.example.letscareer.always.repository;

import com.example.letscareer.always.domain.Always;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlwaysRepository extends JpaRepository<Always, Long> {
}
