package com.example.letscareer.always.domain;

import com.example.letscareer.schedule.domain.Progress;
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
public class Always {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alwaysId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @NotNull
    private User user;

    private String company;
    private String department;

    @Enumerated(EnumType.STRING)
    private Progress progress;

    private String url;

}
