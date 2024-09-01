package com.example.letscareer.self_intro.repository;

import com.example.letscareer.self_intro.domain.SelfIntro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfIntroRepository extends JpaRepository<SelfIntro, Long> {
}
