package com.example.letscareer.stage.domain;

import com.example.letscareer.schedule.domain.Schedule;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    private Type type;

    private LocalDate date;
    private String midName;

    @Column(name = "`order`")
    private Integer order;

    @Enumerated(EnumType.STRING)
    private Status status;
}
