package com.example.letscareer.schedule.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.int_review.domain.repository.IntReviewRepository;
import com.example.letscareer.mid_review.domain.repository.MidReviewRepository;
import com.example.letscareer.schedule.domain.model.Progress;
import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.schedule.domain.dto.*;
import com.example.letscareer.schedule.domain.dto.response.*;
import com.example.letscareer.schedule.domain.dto.request.SchedulePostRequest;
import com.example.letscareer.schedule.domain.dto.request.UpdateScheduleProgressRequest;
import com.example.letscareer.schedule.domain.repository.ScheduleRepository;
import com.example.letscareer.stage.domain.model.Stage;
import com.example.letscareer.stage.domain.model.Status;
import com.example.letscareer.stage.domain.repository.StageRepository;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static com.example.letscareer.common.exception.enums.ErrorCode.SCHEDULE_NOT_FOUND_EXCEPTION;
import static com.example.letscareer.common.exception.enums.ErrorCode.USER_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final StageRepository stageRepository;
    private final ScheduleRepository scheduleRepository;
    private final IntReviewRepository intReviewRepository;
    private final MidReviewRepository midReviewRepository;

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
    public AlwaysResponse getAlwaysList(final Long userId, final int page, final int size) {
        // 사용자 존재 여부 확인
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND_EXCEPTION);
        }

        // 페이지네이션 설정
        Pageable pageable = PageRequest.of(page - 1, size);

        // 사용자 ID, always 조회
        Page<Schedule> schedulePage = scheduleRepository.findAllByUserUserIdAndAlwaysTrue(userId, pageable);

        // 항상 일정 목록을 DTO로 변환
        List<AlwaysDTO> alwaysList = new ArrayList<>();
        for (Schedule schedule : schedulePage) {
            Long scheduleId = schedule.getScheduleId();
            String company = schedule.getCompany();
            String department = schedule.getDepartment();

            // 관련된 Stage 정보 가져오기 (최대 order 기준)
            Optional<Stage> stageOptional = stageRepository.findTopByScheduleScheduleIdOrderByOrderDesc(scheduleId);
            Long stageId = stageOptional.map(Stage::getStageId).orElse(null);
            String status = stageOptional.map(Stage::getStatus).get().getValue();

            // DTO로 변환하여 리스트에 추가
            alwaysList.add(new AlwaysDTO(scheduleId, stageId, company, department, status));
        }

        return new AlwaysResponse(page, size, alwaysList);
    }
    public FastReviewListResponse getFastReviews(final Long userId, final int page, final int size){
        LocalDate today = LocalDate.now();
        LocalDate threeDaysLater = today.plusDays(4); // D+3까지 포함

        Pageable pageable = PageRequest.of(page - 1, size); // JPA는 페이지 인덱스가 0부터 시작

        // 사용자 ID로 모든 Schedule 조회
        Page<Schedule> schedulePage = scheduleRepository.findAllByUserUserId(userId, pageable);

        List<FastDTO> fastReviews = new ArrayList<>();

        int cnt = 0; // D+3까지 회고 없는 stage 개수

        for (Schedule schedule : schedulePage) {
            // 오늘부터 D+3 사이의 Stage를 조회
            List<Stage> stages = stageRepository.findAllByScheduleAndDateBetween(schedule, today, threeDaysLater);

            for (Stage stage : stages) {
                boolean hasIntReview = intReviewRepository.existsByStage(stage);
                boolean hasMidReview = midReviewRepository.existsByStage(stage);

                if (!hasIntReview && !hasMidReview) {
                    // 회고가 없는 Stage만 리스트에 추가
                    cnt++; // 회고 없는 stage 개수 증가
                    fastReviews.add(new FastDTO(
                            stage.getStageId(),
                            schedule.getScheduleId(),
                            schedule.getCompany(),
                            schedule.getDepartment()
                    ));
                }
            }
        }

        return new FastReviewListResponse(
                page,
                size,
                cnt,
                fastReviews
        );
    }
    public CompanyReviewListResponse getCompanyReviewList(final Long userId, final int page, final int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // JPA는 페이지 인덱스가 0부터 시작
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        // 사용자 ID로 모든 Schedule 조회
        Page<Schedule> schedulePage = scheduleRepository.findAllByUserUserId(userId, pageable);

        // 기업별로 리뷰를 분류하기 위한 List
        List<CompanyReviewDTO> companies = new ArrayList<>();

        // 스케줄을 순회하면서 관련 스테이지와 회고 여부를 확인
        for (Schedule schedule : schedulePage) {
            String company = schedule.getCompany();

            // 각 회사별 면접과 중간 리뷰 리스트 초기화
            List<CompanyReviewDetailDTO> interviewReviews = new ArrayList<>();
            List<CompanyReviewDetailDTO> midtermReviews = new ArrayList<>();

            // 모든 Stage를 조회
            List<Stage> stages = stageRepository.findAllBySchedule(schedule);

            for (Stage stage : stages) {
                String type = stage.getType().getValue(); // Stage의 type 필드
                boolean isReviewed = false;
                Long reviewId = null; // 리뷰 ID 초기화

                if (type.equals("면접")) {
                    // INT 타입의 스테이지인 경우 IntReview만 확인
                    isReviewed = intReviewRepository.existsByStage(stage);
                    if (isReviewed) {
                        reviewId = intReviewRepository.findIntReviewIdByStageAndUser(stage, user).orElse(null); // 리뷰 ID 가져오기
                    }

                    // 면접 리뷰를 추가
                    CompanyReviewDetailDTO reviewDetail = new CompanyReviewDetailDTO(
                            schedule.getScheduleId(),
                            stage.getStageId(),
                            reviewId,
                            schedule.getDepartment(),
                            stage.getDate(),
                            isReviewed
                    );
                    interviewReviews.add(reviewDetail);

                } else if (type.equals("중간")) {
                    // MID 타입의 스테이지인 경우 MidReview만 확인
                    isReviewed = midReviewRepository.existsByStage(stage);
                    if (isReviewed) {
                        reviewId = midReviewRepository.findMidReviewIdByStageAndUser(stage, user).orElse(null); // 리뷰 ID 가져오기
                    }

                    // 중간 리뷰를 추가
                    CompanyReviewDetailDTO reviewDetail = new CompanyReviewDetailDTO(
                            schedule.getScheduleId(),
                            stage.getStageId(),
                            reviewId,
                            schedule.getDepartment(),
                            stage.getDate(),
                            isReviewed
                    );
                    midtermReviews.add(reviewDetail);
                }
            }

            // 기업별 리뷰 DTO 추가
            companies.add(new CompanyReviewDTO(company, interviewReviews, midtermReviews));
        }

        return new CompanyReviewListResponse(
                page,
                size,
                companies
        );
    }
    @Transactional
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
                    .always(false)
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
            Schedule newSchedule = Schedule.builder()
                    .user(user)
                    .company(request.company())
                    .department(request.department())
                    .url(request.url())
                    .always(true)
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
        }

    }

    @Transactional
    public void updateScheduleProgress(Long userId, Long scheduleId, UpdateScheduleProgressRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));
        Schedule schedule = scheduleRepository.findByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));

        schedule.setProgress(request.progress());
        scheduleRepository.save(schedule);
    }


    private int calculateDday(LocalDate deadline) {
        int dday = Period.between(LocalDate.now(), deadline).getDays();
        return dday;
    }
}

