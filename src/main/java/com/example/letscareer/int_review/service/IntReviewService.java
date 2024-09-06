package com.example.letscareer.int_review.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.int_review.domain.model.IntReview;
import com.example.letscareer.int_review.domain.dto.request.PostIntReviewRequest;
import com.example.letscareer.int_review.domain.dto.response.IntReviewDetailResponse;
import com.example.letscareer.int_review.domain.repository.IntReviewRepository;
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
public class IntReviewService {

    @Autowired
    private final IntReviewRepository intReviewRepository;
    private final ScheduleRepository scheduleRepository;
    private final StageRepository stageRepository;
    private final UserRepository userRepository;

    @Transactional
    public IntReviewDetailResponse getIntReview(Long userId, Long scheduleId, Long stageId, Long intReviewId){
        User user = getUser(userId);
        IntReview intReview = getIntReview(intReviewId, user);
        Schedule schedule = getSchedule(scheduleId, user);
        Stage stage = getStage(stageId, schedule);
        return IntReviewDetailResponse.from(schedule, stage, intReview);
    }

    @Transactional
    public void postIntReview(Long userId, Long scheduleId, Long stageId, PostIntReviewRequest request) {
        User user = getUser(userId);
        Schedule schedule = getSchedule(scheduleId, user);
        Stage stage = getStage(stageId, schedule);
        IntReview intReview = IntReview.of(request, stage, user);
        intReviewRepository.save(intReview);
    }

    private Stage getStage(Long stageId, Schedule schedule) {
        return stageRepository.findByStageIdAndSchedule(stageId, schedule)
                .orElseThrow(() -> new NotFoundException(STAGE_NOT_FOUND_EXCEPTION));
    }

    private Schedule getSchedule(Long scheduleId, User user) {
        return scheduleRepository.findByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));
    }

    private IntReview getIntReview(Long intReviewId, User user) {
        return intReviewRepository.findByIntReviewIdAndUser(intReviewId, user)
                .orElseThrow(() -> new NotFoundException(INT_REVIEW_NOT_FOUND_EXCEPTION));
    }

    private User getUser(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
    }
}
