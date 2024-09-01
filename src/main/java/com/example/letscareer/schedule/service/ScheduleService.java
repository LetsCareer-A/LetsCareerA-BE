package com.example.letscareer.schedule.service;

import com.example.letscareer.always.domain.Always;
import com.example.letscareer.always.repository.AlwaysRepository;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.schedule.domain.Progress;
import com.example.letscareer.schedule.domain.Schedule;
import com.example.letscareer.schedule.dto.StageDTO;
import com.example.letscareer.schedule.dto.request.SchedulePostRequest;
import com.example.letscareer.schedule.dto.response.ScheduleResponse;
import com.example.letscareer.schedule.repository.ScheduleRepository;
import com.example.letscareer.stage.domain.Stage;
import com.example.letscareer.schedule.dto.response.DateClickScheduleResponse;
import com.example.letscareer.schedule.dto.DateScheduleDTO;
import com.example.letscareer.stage.domain.Status;
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
import java.time.ZoneId;
import java.util.*;

import static com.example.letscareer.common.exception.enums.ErrorCode.USER_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final StageRepository stageRepository;
    private final ScheduleRepository scheduleRepository;
    private final AlwaysRepository alwaysRepository;

    public ScheduleResponse getSchedules(final Long userId, final int month, final int page, final int size) {

        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND_EXCEPTION);
        }

        Pageable pageable = PageRequest.of(page - 1, size);

        // user, month 로 stage 찾기
        Page<Stage> stagePage = stageRepository.findAllByUserIdAndMonth(userId, month, pageable);

        int docCount = 0;
        int midCount = 0;
        int interviewCount = 0;

        List<StageDTO> schedules = new ArrayList<>();

        for (Stage stage : stagePage) {
            Schedule schedule = stage.getSchedule();
            if (schedule != null) {
                Long scheduleId = schedule.getScheduleId();
                Long stageId = stage.getStageId();
                String type = stage.getType().getValue();
                LocalDate deadline = stage.getDate();

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
                Integer dday = (deadline != null) ? calculateDday(deadline) : null;

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

         //sort  -1, -3, +1
        schedules.sort(
                Comparator.<StageDTO>comparingInt(dto -> (dto.dday() < 0 ? -1 : 1)) // 음수 dday 우선 정렬
                        .thenComparingInt(dto -> Math.abs(dto.dday()))  // 절대값 기준 정렬
        );
        return new ScheduleResponse(
                page,
                size,
                docCount,
                midCount,
                interviewCount,
                schedules
        );
    }

    public DateClickScheduleResponse getDateSchedules(final Long userId, final LocalDate date) {
        // 사용자가 존재하는지 확인
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND_EXCEPTION);
        }

        // 특정 날짜와 사용자에 해당하는 모든 Stage를 조회
        List<Stage> stages = stageRepository.findAllByUserIdAndDate(userId, date);

        // 스케줄 정보 초기화
        List<DateScheduleDTO> schedules = new ArrayList<>();
        int totalCount = stages.size(); // 해당 날짜의 일정 수
        int plusCount = Math.max(0, totalCount - 2); // 일정 수가 2 이상이면 +n 건으로 표시

        for (Stage stage : stages) {
            Schedule schedule = stage.getSchedule();
            if (schedule != null) {
                Long scheduleId = schedule.getScheduleId();
                Long stageId = stage.getStageId();
                String company = schedule.getCompany();
                String department = schedule.getDepartment();
                String type = stage.getType().getValue();

                // D-day 계산
                Integer dday = (stage.getDate() != null) ? calculateDday(stage.getDate()) : null;

                // 진행 상태
                String progress = schedule.getProgress().getValue();

                // DTO로 변환하여 리스트에 추가
                schedules.add(new DateScheduleDTO(
                        scheduleId,
                        stageId,
                        company,
                        department,
                        type,
                        dday,
                        progress
                ));
            }
        }

        // 결과 반환
        return new DateClickScheduleResponse(
                totalCount,
                plusCount,
                schedules
        );
    }

    public void postSchedule(Long userId,SchedulePostRequest request){
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND_EXCEPTION);
        }

        User user = userOptional.get();

        if (!request.always()){ //schedule 일때
            Schedule newSchedule = Schedule.builder()
                    .user(user)
                    .company(request.company())
                    .department(request.department())
                    .url(request.url())
                    .progress(Progress.DO)
                    .build();
            scheduleRepository.save(newSchedule);
            Stage newStage = Stage.builder()
                    .schedule(newSchedule)
                    .type(request.type())
                    .date(request.date())
                    .midName(request.midname())
                    .order(1)
                    .status(Status.DO)
                    .build();
            stageRepository.save(newStage);
        }else{ //always일때
            Always newAlways = Always.builder()
                    .user(user)
                    .company(request.company())
                    .department(request.department())
                    .progress(Progress.DO)
                    .url(request.url())
                    .build();
            alwaysRepository.save(newAlways);
            Stage newStage = Stage.builder()
                    .always(newAlways)
                    .type(request.type())
                    .date(request.date())
                    .midName(request.midname())
                    .order(1)
                    .status(Status.DO)
                    .build();
            stageRepository.save(newStage);
        }

    }
    private int calculateDday(LocalDate deadline) {
        int dday = Period.between(LocalDate.now(), deadline).getDays();
        return dday;
    }
}

