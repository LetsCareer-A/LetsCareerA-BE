package com.example.letscareer.self_intro.domain;

import com.example.letscareer.stage.domain.Stage;
import com.example.letscareer.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelfIntro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long selfIntroId;

    @Lob
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stageId")
    @NotNull
    private Stage stage;
}
