package com.example.letscareer.appealCareer.service;

import com.example.letscareer.appealCareer.domain.model.AppealCareer;
import com.example.letscareer.appealCareer.domain.dto.request.AddAppealCareersRequest;
import com.example.letscareer.appealCareer.domain.repository.AppealCareerRepository;
import com.example.letscareer.career.domain.repository.CareerRepository;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.schedule.domain.repository.ScheduleRepository;
import com.example.letscareer.stage.domain.model.Stage;
import com.example.letscareer.stage.domain.repository.StageRepository;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.letscareer.common.exception.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AppealCareerService {

    @Autowired
    private final AppealCareerRepository appealCareerRepository;
    private final ScheduleRepository scheduleRepository;
    private final StageRepository stageRepository;
    private final UserRepository userRepository;
    private final CareerRepository careerRepository;

    @Transactional
    public void addAppealCareer(Long userId, Long scheduleId, Long stageId, AddAppealCareersRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        Schedule schedule = scheduleRepository.findByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));
        Stage stage = stageRepository.findByStageIdAndSchedule(stageId, schedule)
                .orElseThrow(() -> new NotFoundException(STAGE_NOT_FOUND_EXCEPTION));

        request.careers().forEach(careerId -> {
            careerRepository.findById(careerId).ifPresent(career -> {
                appealCareerRepository.save(AppealCareer.builder()
                        .career(career)
                        .stage(stage)
                        .build());
            });
        });
    }
}
