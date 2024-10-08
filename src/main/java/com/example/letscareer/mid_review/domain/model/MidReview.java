package com.example.letscareer.mid_review.domain.model;

import com.example.letscareer.mid_review.domain.dto.request.PostMidReviewRequest;
import com.example.letscareer.stage.domain.model.Stage;
import com.example.letscareer.user.domain.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public static MidReview of(PostMidReviewRequest request, Stage stage, User user) {
        return MidReview.builder()
                .freeReview(request.free_review())
                .goal(request.goal())
                .stage(stage)
                .user(user)
                .build();
    }
}
