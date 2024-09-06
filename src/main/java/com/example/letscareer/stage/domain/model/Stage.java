package com.example.letscareer.stage.domain.model;

import com.example.letscareer.schedule.domain.dto.request.SchedulePostRequest;
import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.stage.domain.dto.request.AddStageRequest;
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
    public static Stage toEntity(Schedule schedule, SchedulePostRequest request) {
        return Stage.builder()
                .schedule(schedule)
                .type(request.type())
                .date(request.date())
                .midName(request.midname())
                .order(1)
                .status(Status.DO)
                .build();
    }

    public static Stage of(Schedule schedule, AddStageRequest request) {
        return Stage.builder()
                .schedule(schedule)
                .type(request.type())
                .date(request.date())
                .midName(request.mid_name())
                .status(Status.DO)
                .order(schedule.getStages().size() + 1)
                .build();
    }
}
