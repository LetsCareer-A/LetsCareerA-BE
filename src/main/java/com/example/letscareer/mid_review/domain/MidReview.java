package com.example.letscareer.mid_review.domain;

import com.example.letscareer.stage.domain.Stage;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MidReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long midReviewId;

    @Lob
    private String freeReview;
    @Lob
    private String goal;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stageId")
    @NotNull
    private Stage stage;
}
