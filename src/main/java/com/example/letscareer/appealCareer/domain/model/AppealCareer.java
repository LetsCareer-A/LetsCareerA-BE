package com.example.letscareer.appealCareer.domain.model;


import com.example.letscareer.career.domain.model.Career;
import com.example.letscareer.stage.domain.model.Stage;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppealCareer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appealCareerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stageId")
    private Stage stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "careerId")
    private Career career;

}
