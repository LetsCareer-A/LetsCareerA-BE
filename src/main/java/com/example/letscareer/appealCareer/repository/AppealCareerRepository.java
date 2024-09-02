package com.example.letscareer.appealCareer.repository;

import com.example.letscareer.appealCareer.domain.AppealCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppealCareerRepository extends JpaRepository<AppealCareer, Long> {
}
