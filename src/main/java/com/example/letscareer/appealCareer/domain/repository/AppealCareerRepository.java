package com.example.letscareer.appealCareer.domain.repository;

import com.example.letscareer.appealCareer.domain.model.AppealCareer;
import com.example.letscareer.career.domain.model.Career;
import com.example.letscareer.stage.domain.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppealCareerRepository extends JpaRepository<AppealCareer, Long> {
    List<AppealCareer> findByStage(Stage stage);
    List<AppealCareer> findByStageAndCareerIn(Stage stage, List<Career> careers);
}
