package com.example.letscareer.mid_review.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.mid_review.domain.model.MidReview;
import com.example.letscareer.mid_review.domain.dto.request.PostMidReviewRequest;
import com.example.letscareer.mid_review.domain.dto.response.MidReviewDetailResponse;
import com.example.letscareer.mid_review.domain.repository.MidReviewRepository;
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

import java.util.Optional;

import static com.example.letscareer.common.exception.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MidReviewService {

    @Autowired
    private final MidReviewRepository midReviewRepository;
    private final ScheduleRepository scheduleRepository;
    private final StageRepository stageRepository;
    private final UserRepository userRepository;

    @Transactional
    public MidReviewDetailResponse getMidReview(Long userId, Long scheduleId,Long stageId, Long midReviewId){
        User user = getUser(userId);
        MidReview midReview = getMidReview(midReviewId, user);
        Schedule schedule = getSchedule(scheduleId, user);
        Stage stage = getStage(stageId, schedule);
        return MidReviewDetailResponse.from(schedule, stage, midReview);
    }

    @Transactional
    public void postMidReview(Long userId, Long scheduleId, Long stageId, PostMidReviewRequest request) {
        User user = getUser(userId);
        Schedule schedule = getSchedule(scheduleId, user);
        Stage stage = getStage(stageId, schedule);

        MidReview midReview = MidReview.of(request, stage, user);
        midReviewRepository.save(midReview);
    }

    private Stage getStage(Long stageId, Schedule schedule) {
        return stageRepository.findByStageIdAndSchedule(stageId, schedule)
                .orElseThrow(() -> new NotFoundException(STAGE_NOT_FOUND_EXCEPTION));
    }

    private Schedule getSchedule(Long scheduleId, User user) {
        return scheduleRepository.findByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));
    }

    private User getUser(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
    }

    private MidReview getMidReview(Long midReviewId, User user) {
        return midReviewRepository.findByMidReviewIdAndUser(midReviewId, user)
                .orElseThrow(() -> new NotFoundException(MID_REVIEW_NOT_FOUND_EXCEPTION));
    }
}
