package com.example.letscareer.mid_review.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.int_review.domain.IntReview;
import com.example.letscareer.int_review.repository.IntReviewRepository;
import com.example.letscareer.mid_review.domain.MidReview;
import com.example.letscareer.mid_review.dto.FastDTO;
import com.example.letscareer.mid_review.dto.request.PostMidReviewRequest;
import com.example.letscareer.mid_review.dto.response.FastReviewsResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.example.letscareer.common.exception.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MidReviewService {

    @Autowired
    private final MidReviewRepository midReviewRepository;
    private final IntReviewRepository intReviewRepository;
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
    public static int calculatePlusDays(LocalDate date) {
        LocalDate now = LocalDate.now();

        // 현재 날짜와 대상 날짜 간의 차이를 일 단위로 계산
        long daysBetween = ChronoUnit.DAYS.between(now, date);

        return (int)daysBetween;
    }
}
