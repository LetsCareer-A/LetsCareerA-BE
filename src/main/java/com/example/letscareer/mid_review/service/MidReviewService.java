package com.example.letscareer.mid_review.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.mid_review.domain.MidReview;
import com.example.letscareer.mid_review.dto.request.PostMidReviewRequest;
import com.example.letscareer.mid_review.repository.MidReviewRepository;
import com.example.letscareer.schedule.domain.Schedule;
import com.example.letscareer.schedule.repository.ScheduleRepository;
import com.example.letscareer.stage.domain.Stage;
import com.example.letscareer.stage.repository.StageRepository;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void postMidReview(Long userId, Long scheduleId, Long stageId, PostMidReviewRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));
        Stage stage = stageRepository.findById(stageId)
                .orElseThrow(() -> new NotFoundException(STAGE_NOT_FOUND_EXCEPTION));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));

        MidReview midReview = MidReview.builder()
                        .freeReview(request.free_review())
                        .goal(request.goal())
                        .stage(stage)
                        .user(user)
                        .build();

        midReviewRepository.save(midReview);
    }
}
