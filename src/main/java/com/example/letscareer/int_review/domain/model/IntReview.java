package com.example.letscareer.int_review.domain.model;

import com.example.letscareer.stage.domain.model.Stage;
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
public class IntReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long intReviewId;

    @Lob
    private String method;
    @Lob
    private String questions;
    @Lob
    private String feelings;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stageId")
    @NotNull
    private Stage stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
