package com.example.letscareer.schedule.domain.model;

import com.example.letscareer.stage.domain.model.Stage;
import com.example.letscareer.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @NotNull
    private User user;

    private String company;
    private String department;
    private boolean always; //not null

    @Enumerated(EnumType.STRING)
    private Progress progress;

    private String url;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Stage> stages;
}
