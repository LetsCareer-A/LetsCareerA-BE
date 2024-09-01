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

    public FastReviewsResponse getFastReviews(Long userId, int page, int size) {

        LocalDate today = LocalDate.now();
        LocalDate threeDaysLater = today.plusDays(4); //3일 뒤까지 포함

        Pageable pageable = PageRequest.of(page - 1, size); // JPA는 페이지 인덱스가 0부터 시작

        // 면접 회고 조회
        Page<IntReview> intReviews = intReviewRepository.findAllByUserIdAndStageDeadlineWithin3Days(userId, today, threeDaysLater, pageable);

        // 중간 회고 조회
        Page<MidReview> midReviews = midReviewRepository.findAllByUserIdAndStageDeadlineWithin3Days(userId, today, threeDaysLater,  pageable);

        List<FastDTO> fastReviews = new ArrayList<>();

        // 면접 회고 데이터를 응답 형식에 맞게 변환
        intReviews.forEach(ir -> {
            if (ir.getStage().getAlways() == null) {
                FastDTO fastDTO = new FastDTO(
                        ir.getStage().getStageId(),
                        ir.getIntReviewId(),
                        ir.getStage().getSchedule().getCompany(),
                        ir.getStage().getSchedule().getCompany(),
                        "면접",
                        calculatePlusDays(ir.getStage().getDate())
                );
                fastReviews.add(fastDTO);
            }else{
                FastDTO fastDTO = new FastDTO(
                        ir.getStage().getStageId(),
                        ir.getIntReviewId(),
                        ir.getStage().getAlways().getCompany(),
                        ir.getStage().getAlways().getCompany(),
                        "면접",
                        calculatePlusDays(ir.getStage().getDate())
                );
                fastReviews.add(fastDTO);
            }
        });

        // 중간 회고 데이터를 응답 형식에 맞게 변환
        midReviews.forEach(mr -> {
            if (mr.getStage().getAlways() == null) {
                FastDTO fastDTO = new FastDTO(
                        mr.getStage().getStageId(),
                        mr.getMidReviewId(),
                        mr.getStage().getSchedule().getCompany(),
                        mr.getStage().getSchedule().getCompany(),
                        "중간",
                        calculatePlusDays(mr.getStage().getDate())
                );
                fastReviews.add(fastDTO);
            }else{
                FastDTO fastDTO = new FastDTO(
                        mr.getStage().getStageId(),
                        mr.getMidReviewId(),
                        mr.getStage().getAlways().getCompany(),
                        mr.getStage().getAlways().getCompany(),
                        "중간",
                        calculatePlusDays(mr.getStage().getDate())
                );
                fastReviews.add(fastDTO);
            }
        });

        // plusDay 값으로 fastReviews 리스트 정렬
        fastReviews.sort(Comparator.comparingInt(FastDTO::plusDay));

        return new FastReviewsResponse(
                page,
                size,
                fastReviews.size(),
                fastReviews
        );
    }
    public static int calculatePlusDays(LocalDate date) {
        LocalDate now = LocalDate.now();

        // 현재 날짜와 대상 날짜 간의 차이를 일 단위로 계산
        long daysBetween = ChronoUnit.DAYS.between(now, date);

        return (int)daysBetween;
    }
}
