package com.example.letscareer.schedule.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.schedule.domain.Schedule;
import com.example.letscareer.schedule.dto.response.StageDTO;
import com.example.letscareer.schedule.dto.response.ScheduleResponse;
import com.example.letscareer.schedule.repository.ScheduleRepository;
import com.example.letscareer.stage.domain.Stage;
import com.example.letscareer.stage.repository.StageRepository;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.example.letscareer.common.exception.enums.ErrorCode.USER_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final StageRepository stageRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponse getSchedules(final Long userId, final int month, final int page, final int size) {
        // Retrieve user
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND_EXCEPTION);
        }

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Schedule> schedulePage = scheduleRepository.findAllByUserIdAndMonth(userId, month, pageable);

        // 초기화
        int docCount = 0;
        int midCount = 0;
        int interviewCount = 0;

        // response 안 shceudles 초기화
        List<StageDTO> schedules = new ArrayList<>();

        // schedule 마다 stage 찾기
        for (Schedule schedule : schedulePage) {
            Long scheduleId = schedule.getScheduleId();
            List<Stage> stages = stageRepository.findAllByScheduleScheduleId(scheduleId);

            for (Stage stage : stages) {
                Long stageId = stage.getStageId();
                String type = stage.getType().name();
                String deadline = stage.getDate().toString();

                // Increment count based on type
                switch (stage.getType()) {
                    case DOC:
                        docCount++;
                        break;
                    case MID:
                        midCount++;
                        break;
                    case INT:
                        interviewCount++;
                        break;
                }

                // Calculate dday
                Integer dday = (deadline != null) ? calculateDday(deadline) : null;

                // Map each stage to ScheduleDTO and add to list
                schedules.add(new StageDTO(
                        scheduleId,
                        stageId,
                        schedule.getCompany(),
                        schedule.getDepartment(),
                        type,
                        deadline,
                        dday,
                        schedule.getProgress()
                ));
            }
        }
        // dday 기준으로 정렬 -1, -3, +1
        schedules.sort(Comparator.comparingInt((StageDTO dto) -> dto.dday()).thenComparingInt(dto -> Math.abs(dto.dday())));
        return new ScheduleResponse(
                page,
                size,
                docCount,
                midCount,
                interviewCount,
                schedules
        );
    }

    private int calculateDday(String deadline) {
        LocalDate deadlineDate = LocalDate.parse(deadline);
        return Period.between(LocalDate.now(), deadlineDate).getDays();
    }
}

