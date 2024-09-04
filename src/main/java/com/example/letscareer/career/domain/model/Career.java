package com.example.letscareer.career.domain.model;


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
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @NotNull
    private User user;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Lob //긴 텍스트 (TEXT)
    private String situation;
    @Lob
    private String task;
    @Lob
    private String action;
    @Lob
    private String result;
}
