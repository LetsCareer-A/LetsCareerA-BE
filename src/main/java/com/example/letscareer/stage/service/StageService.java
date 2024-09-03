package com.example.letscareer.stage.service;

import com.example.letscareer.appealCareer.domain.AppealCareer;
import com.example.letscareer.appealCareer.repository.AppealCareerRepository;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.mid_review.domain.MidReview;
import com.example.letscareer.mid_review.repository.MidReviewRepository;
import com.example.letscareer.schedule.domain.Schedule;
import com.example.letscareer.schedule.repository.ScheduleRepository;
import com.example.letscareer.self_intro.domain.SelfIntro;
import com.example.letscareer.self_intro.dto.SelfIntroDTO;
import com.example.letscareer.self_intro.repository.SelfIntroRepository;
import com.example.letscareer.stage.domain.Stage;
import com.example.letscareer.stage.domain.Status;
import com.example.letscareer.stage.domain.Type;
import com.example.letscareer.stage.dto.AppealCareerDTO;
import com.example.letscareer.stage.dto.ReviewDTO;
import com.example.letscareer.stage.dto.StageDTO;
import com.example.letscareer.stage.dto.request.AddStageRequest;
import com.example.letscareer.stage.dto.request.UpdateStageStatusRequest;
import com.example.letscareer.stage.dto.response.AddStageResponse;
import com.example.letscareer.stage.dto.response.GetDocumentStageResponse;
import com.example.letscareer.stage.dto.response.GetMidStageResponse;
import com.example.letscareer.stage.dto.response.GetStagesResponse;
import com.example.letscareer.stage.repository.StageRepository;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.repository.UserRepository;
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

    @Transactional
    public AddStageResponse addStage(Long userId, Long scheduleId, AddStageRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        Schedule schedule = scheduleRepository.findByUserAndScheduleId(user, scheduleId)
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
                stage.getType().equals(Type.MID) ? stage.getMidName() : "",
                stage.getStatus().getValue(),
                stage.getDate(),
                dday);
    }

    @Transactional
    public void updateStageStatus(Long userId, Long scheduleId, Long stageId, UpdateStageStatusRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        Schedule schedule = scheduleRepository.findByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));
        Stage stage = stageRepository.findByStageIdAndSchedule(stageId, schedule)
                .orElseThrow(() -> new NotFoundException(STAGE_NOT_FOUND_EXCEPTION));

        stage.setStatus(request.status());
        stageRepository.save(stage);
    }

    private int calculateDday(LocalDate deadline) {
        int dday = Period.between(LocalDate.now(), deadline).getDays();
        return dday;
    }

    public GetStagesResponse getStages(Long userId, Long scheduleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        Schedule schedule = scheduleRepository.findByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));

        // 해당 스케쥴에 속한 모든 스테이지 조회
        List<Stage> stages = stageRepository.findBySchedule(schedule);
        List<StageDTO> stageDTOs = new ArrayList<>();

        for(Stage stage : stages) {
            // D-day 계산
            String dday = (!schedule.isAlways()) ? String.valueOf(calculateDday(stage.getDate())) : "";
            stageDTOs.add(
                    new StageDTO(
                            stage.getStageId(),
                            stage.getOrder(),
                            stage.getType().getValue(),
                            stage.getType().equals(Type.MID) ? stage.getMidName() : "",
                            stage.getStatus().getValue(),
                            schedule.isAlways() ? "" : stage.getDate().toString(),
                            dday
                    )
            );
        }

        return new GetStagesResponse(
                schedule.getScheduleId(),
                schedule.getCompany(),
                schedule.getDepartment(),
                schedule.getUrl(),
                schedule.getProgress().getValue(),
                schedule.isAlways(),
                stageDTOs);
    }

    public GetDocumentStageResponse getDocumentStage(Long userId, Long scheduleId, Long stageId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        Schedule schedule = scheduleRepository.findByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));
        Stage stage = stageRepository.findByScheduleAndStageIdAndType(schedule, stageId, Type.DOC)
                .orElseThrow(() -> new NotFoundException(DOC_STAGE_NOT_FOUND_EXCEPTION));

        List<SelfIntro> selfIntros = selfIntroRepository.findByStage(stage);
        List<SelfIntroDTO> selfIntroDTOs = new ArrayList<>();
        for(SelfIntro selfIntro : selfIntros) {
            selfIntroDTOs.add(
                    new SelfIntroDTO(
                            selfIntro.getTitle(),
                            selfIntro.getSequence(),
                            selfIntro.getContent()
                    )
            );
        }

        List<AppealCareer> appealCareers = appealCareerRepository.findByStage(stage);
        List<AppealCareerDTO> appealCareerDTOs = new ArrayList<>();
        for(AppealCareer appealCareer : appealCareers) {
            appealCareerDTOs.add(
                    new AppealCareerDTO(
                            appealCareer.getCareer().getCareerId(),
                            appealCareer.getCareer().getCategory().getValue(),
                            appealCareer.getCareer().getTitle()
                    )
            );
        }

        return new GetDocumentStageResponse(
                stage.getStageId(),
                stage.getType().getValue(),
                selfIntroDTOs,
                appealCareerDTOs
        );
    }

    public GetMidStageResponse getMidStage(Long userId, Long scheduleId, Long stageId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        Schedule schedule = scheduleRepository.findByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));
        Stage stage = stageRepository.findByScheduleAndStageIdAndType(schedule, stageId, Type.MID)
                .orElseThrow(() -> new NotFoundException(MID_STAGE_NOT_FOUND_EXCEPTION));

        Optional<MidReview> midReview = midReviewRepository.findByStage(stage);

        if(midReview.isEmpty()) {
            return new GetMidStageResponse(
                    true,
                    null
            );
        }
        else {
            ReviewDTO reviewDTO = new ReviewDTO(
                    midReview.get().getMidReviewId(),
                    midReview.get().getFreeReview(),
                    midReview.get().getGoal()
            );
            return new GetMidStageResponse(
                    false,
                    reviewDTO
            );
        }
    }
}
