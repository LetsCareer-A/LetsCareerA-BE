package com.example.letscareer.self_intro.domain.model;

import com.example.letscareer.self_intro.domain.dto.SelfIntroDTO;
import com.example.letscareer.stage.domain.model.Stage;
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

    private String title;

    private int sequence;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stageId")
    @NotNull
    private Stage stage;

    public static SelfIntro of(SelfIntroDTO selfIntroDTO, Stage stage) {
        return SelfIntro.builder()
                .title(selfIntroDTO.title())
                .sequence(selfIntroDTO.sequence())
                .content(selfIntroDTO.content())
                .stage(stage)
                .build();
    }
}
