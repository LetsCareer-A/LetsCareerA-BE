package com.example.letscareer.appealCareer.repository;

import com.example.letscareer.appealCareer.domain.AppealCareer;
import com.example.letscareer.stage.domain.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppealCareerRepository extends JpaRepository<AppealCareer, Long> {
    List<AppealCareer> findByStage(Stage stage);
}
