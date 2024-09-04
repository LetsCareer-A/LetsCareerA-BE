package com.example.letscareer.self_intro.domain.repository;

import com.example.letscareer.self_intro.domain.model.SelfIntro;
import com.example.letscareer.stage.domain.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelfIntroRepository extends JpaRepository<SelfIntro, Long> {
    void deleteByStage(Stage stage);

    List<SelfIntro> findByStage(Stage stage);
}
