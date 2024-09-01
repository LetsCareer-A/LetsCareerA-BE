package com.example.letscareer.stage.domain;

import com.example.letscareer.always.domain.Always;
import com.example.letscareer.schedule.domain.Schedule;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "(schedule_id IS NOT NULL AND always_id IS NULL) OR (schedule_id IS NULL AND always_id IS NOT NULL)")
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "always_id")
    private Always always;

    @Enumerated(EnumType.STRING)
    private Type type;

    private LocalDate date;
    private String midName;

    @Column(name = "`order`")
    private Integer order;

    @Enumerated(EnumType.STRING)
    private Status status;
}
