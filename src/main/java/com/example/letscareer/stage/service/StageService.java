package com.example.letscareer.stage.service;

import com.example.letscareer.appealCareer.domain.model.AppealCareer;
import com.example.letscareer.appealCareer.domain.repository.AppealCareerRepository;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.int_review.domain.model.IntReview;
import com.example.letscareer.int_review.domain.repository.IntReviewRepository;
import com.example.letscareer.mid_review.domain.model.MidReview;
import com.example.letscareer.mid_review.domain.repository.MidReviewRepository;
import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.schedule.domain.repository.ScheduleRepository;
import com.example.letscareer.self_intro.domain.model.SelfIntro;
import com.example.letscareer.self_intro.domain.dto.SelfIntroDTO;
import com.example.letscareer.self_intro.domain.repository.SelfIntroRepository;
import com.example.letscareer.stage.domain.dto.converter.StageConverter;
import com.example.letscareer.stage.domain.model.Stage;
import com.example.letscareer.stage.domain.model.Status;
import com.example.letscareer.stage.domain.model.Type;
import com.example.letscareer.stage.domain.dto.AppealCareerDTO;
import com.example.letscareer.stage.domain.dto.IntReviewDTO;
import com.example.letscareer.stage.domain.dto.MidReviewDTO;
import com.example.letscareer.stage.domain.dto.StageDTO;
import com.example.letscareer.stage.domain.dto.request.AddStageRequest;
import com.example.letscareer.stage.domain.dto.request.UpdateStageStatusRequest;
import com.example.letscareer.stage.domain.dto.response.*;
import com.example.letscareer.stage.domain.repository.StageRepository;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.letscareer.common.exception.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class StageService {

    @Autowired
    private final StageRepository stageRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final SelfIntroRepository selfIntroRepository;
    private final AppealCareerRepository appealCareerRepository;
    private final MidReviewRepository midReviewRepository;
    private final IntReviewRepository intReviewRepository;

    @Transactional
    public AddStageResponse addStage(Long userId, Long scheduleId, AddStageRequest request) {
        User user = getUser(userId);
        Schedule schedule = getSchedule(scheduleId, user);
        Stage stage = Stage.of(schedule, request);

        stageRepository.save(stage);

        Integer dday = (request.date() != null) ? calculateDday(request.date()) : null;
        return AddStageResponse.from(stage, dday);
    }

    @Transactional
    public void updateStageStatus(Long userId, Long scheduleId, Long stageId, UpdateStageStatusRequest request) {
        User user = getUser(userId);
        Schedule schedule = getSchedule(scheduleId, user);
        Stage stage = getStage(stageId, schedule);
        stage.setStatus(request.status());
        stageRepository.save(stage);
    }

    @Transactional
    public GetStagesResponse getStages(Long userId, Long scheduleId) {
        User user = getUser(userId);
        Schedule schedule = getSchedule(scheduleId, user);

        // 해당 스케쥴에 속한 모든 스테이지 조회
        List<Stage> stages = stageRepository.findBySchedule(schedule);
        List<StageDTO> stageDTOs = StageConverter.toStageDTOs(stages, schedule);;

        return GetStagesResponse.from(schedule, stageDTOs);
    }

    @Transactional
    public GetDocumentStageResponse getDocumentStage(Long userId, Long scheduleId, Long stageId) {
        User user = getUser(userId);
        Schedule schedule = getSchedule(scheduleId, user);
        Stage stage = getStage(stageId, schedule);

        // SelfIntroDTO 변환
        List<SelfIntro> selfIntros = selfIntroRepository.findByStage(stage);
        List<SelfIntroDTO> selfIntroDTOs = StageConverter.toSelfIntroDTOs(selfIntros);

        // AppealCareerDTO 변환
        List<AppealCareer> appealCareers = appealCareerRepository.findByStage(stage);
        List<AppealCareerDTO> appealCareerDTOs = StageConverter.toAppealCareerDTOs(appealCareers);

        return GetDocumentStageResponse.from(stage, selfIntroDTOs, appealCareerDTOs);
    }

    @Transactional
    public GetMidStageResponse getMidStage(Long userId, Long scheduleId, Long stageId) {
        User user = getUser(userId);
        Schedule schedule = getSchedule(scheduleId, user);
        Stage stage = getStage(stageId, schedule);
        Optional<MidReview> midReview = midReviewRepository.findByStage(stage);

        return midReview
                .map(review -> new GetMidStageResponse(false, MidReviewDTO.from(review)))
                .orElseGet(() -> new GetMidStageResponse(true, null));
    }

    @Transactional
    public GetInterviewStageResponse getInterviewStage(Long userId, Long scheduleId, Long stageId) {
        User user = getUser(userId);
        Schedule schedule = getSchedule(scheduleId, user);
        Stage stage = getStage(stageId, schedule);

        // AppealCareerDTO 변환
        List<AppealCareer> appealCareers = appealCareerRepository.findByStage(stage);
        List<AppealCareerDTO> appealCareerDTOs = StageConverter.toAppealCareerDTOs(appealCareers);

        // IntReviewDTO 변환
        Optional<IntReviewDTO> intReviewDTO = intReviewRepository.findByStage(stage)
                .map(IntReviewDTO::from);

        return new GetInterviewStageResponse(intReviewDTO.isEmpty(), intReviewDTO.orElse(null), appealCareerDTOs);
    }

    private Stage getStage(Long stageId, Schedule schedule) {
        return stageRepository.findByStageIdAndSchedule(stageId, schedule)
                .orElseThrow(() -> new NotFoundException(STAGE_NOT_FOUND_EXCEPTION));
    }

    private int calculateDday(LocalDate deadline) {
        int dday = Period.between(LocalDate.now(), deadline).getDays();
        return dday;
    }


    private Schedule getSchedule(Long scheduleId, User user) {
        return scheduleRepository.findByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
    }

}