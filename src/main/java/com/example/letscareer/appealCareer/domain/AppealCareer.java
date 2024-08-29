package com.example.letscareer.appealCareer.domain;


import com.example.letscareer.career.domain.Career;
import com.example.letscareer.stage.domain.Stage;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stageIdd")
    private Stage stage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "careeId")
    private Career career;

}
