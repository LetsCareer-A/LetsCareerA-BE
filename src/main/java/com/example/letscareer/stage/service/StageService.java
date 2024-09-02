package com.example.letscareer.stage.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.schedule.domain.Schedule;
import com.example.letscareer.schedule.repository.ScheduleRepository;
import com.example.letscareer.stage.domain.Stage;
import com.example.letscareer.stage.domain.Status;
import com.example.letscareer.stage.dto.request.AddStageRequest;
import com.example.letscareer.stage.dto.response.AddStageResponse;
import com.example.letscareer.stage.repository.StageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

import static com.example.letscareer.common.exception.enums.ErrorCode.SCHEDULE_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class StageService {

    @Autowired
    private final StageRepository stageRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public AddStageResponse addStage(Long userId, Long scheduleId, AddStageRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));

        Stage stage = Stage.builder()
                .schedule(schedule)
                .type(request.type())
                .midName(request.mid_name())
                .date(request.date())
                .status(Status.DO)
                .order(schedule.getStages().size() + 1)
                .build();

        stageRepository.save(stage);

        // D-day 계산
        Integer dday = (request.date() != null) ? calculateDday(request.date()) : null;

        return new AddStageResponse(
                stage.getStageId(),
                stage.getType().getValue(),
                stage.getMidName(),
                stage.getStatus().getValue(),
                stage.getDate(),
                dday);
    }

    private int calculateDday(LocalDate deadline) {
        int dday = Period.between(LocalDate.now(), deadline).getDays();
        return dday;
    }

}
