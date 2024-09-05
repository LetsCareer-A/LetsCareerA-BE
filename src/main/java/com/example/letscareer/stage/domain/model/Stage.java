package com.example.letscareer.stage.domain.model;

import com.example.letscareer.schedule.domain.model.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

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

    public Integer calculateDday() {
        if (this.date == null) return null;
        return Period.between(this.date, LocalDate.now()).getDays();
    }
}
