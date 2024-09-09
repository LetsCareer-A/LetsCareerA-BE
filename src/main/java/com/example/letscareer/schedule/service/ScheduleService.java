package com.example.letscareer.schedule.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.int_review.domain.repository.IntReviewRepository;
import com.example.letscareer.mid_review.domain.repository.MidReviewRepository;
import com.example.letscareer.schedule.domain.dto.*;
import com.example.letscareer.schedule.domain.dto.request.SchedulePostRequest;
import com.example.letscareer.schedule.domain.dto.request.UpdateScheduleProgressRequest;
import com.example.letscareer.schedule.domain.dto.response.*;
import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.schedule.domain.repository.ScheduleRepository;
import com.example.letscareer.stage.domain.model.Stage;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public ScheduleResponse getSchedulesComing(final Long userId, final int month, final int page, final int size) {

        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND_EXCEPTION);
        }

        Pageable pageable = PageRequest.of(page - 1, size);

        // user, month 로 stage 찾기
        Page<Stage> stagePage = stageRepository.findAllByUserIdAndMonth(userId, month, pageable);
        long total = stagePage.getTotalElements();

        List<ScheduleDTO> schedules = new ArrayList<>();

        for (Stage stage : stagePage) {
            Schedule schedule = stage.getSchedule();
            if (schedule != null) {
                Long scheduleId = schedule.getScheduleId();
                Long stageId = stage.getStageId();
                String type = stage.getType().getValue();
                String status = stage.getStatus().getValue();
                LocalDate deadline = stage.getDate();

                Integer dday = (deadline != null) ? stage.calculateDday() : null;

                schedules.add(new ScheduleDTO(
                        scheduleId,
                        stageId,
                        schedule.getCompany(),
                        schedule.getDepartment(),
                        type,
                        deadline,
                        dday,
                        status
                ));
            }
        }

         //sort  -1, -3, +1
        schedules.sort(
                Comparator.<ScheduleDTO>comparingInt(dto -> (dto.dday() < 0 ? -1 : 1)) // 음수 dday 우선 정렬
                        .thenComparingInt(dto -> Math.abs(dto.dday()))  // 절대값 기준 정렬
        );
        return new ScheduleResponse(
                page,
                size,
                total,
                schedules
        );
    }
    public CalendarResponse getSchedulesCalendar(final Long userId, final int month) {

        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND_EXCEPTION);
        }

        // user, month 로 stage 찾기
        List<Stage> stages = stageRepository.findAllByUserIdAndMonth(userId, month);

        int docCount = 0;
        int midCount = 0;
        int interviewCount = 0;

        List<ScheduleDTO> schedules = new ArrayList<>();

        for (Stage stage : stages) {
            Schedule schedule = stage.getSchedule();
            if (schedule != null) {
                Long scheduleId = schedule.getScheduleId();
                Long stageId = stage.getStageId();
                String type = stage.getType().getValue();
                String status = stage.getStatus().getValue();
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
                Integer dday = (deadline != null) ? stage.calculateDday() : null;

                schedules.add(new ScheduleDTO(
                        scheduleId,
                        stageId,
                        schedule.getCompany(),
                        schedule.getDepartment(),
                        type,
                        deadline,
                        dday,
                        status
                ));
            }
        }

        return new CalendarResponse(
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
                Integer dday = (stage.getDate() != null) ? stage.calculateDday() : null;

                // 진행 상태
                String progress = stage.getStatus().getValue();

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
        long total = schedulePage.getTotalElements();
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

        return new AlwaysResponse(page, size, total,alwaysList);
    }
    public FastReviewListResponse getFastReviews(final Long userId, final int page, final int size) {
        LocalDate today = LocalDate.now();
        LocalDate threeDaysPrevious = today.minusDays(4);

        Pageable pageable = PageRequest.of(page - 1, size); // 페이지네이션을 Stage 단위로 처리

        // 먼저 userId로 Schedule 목록을 가져옴
        List<Schedule> schedules = scheduleRepository.findAllByUserUserId(userId);

        // QueryDSL로 Stage 조회
        Page<Stage> stagePage = stageRepository.findAllByScheduleInAndDateBetweenAndIntReviewNotExistsAndMidReviewNotExists(
                schedules, threeDaysPrevious, today, pageable);

        List<FastDTO> fastReviews = new ArrayList<>();

        for (Stage stage : stagePage) {
            fastReviews.add(new FastDTO(
                    stage.getStageId(),
                    stage.getSchedule().getScheduleId(),
                    stage.getSchedule().getCompany(),
                    stage.getSchedule().getDepartment()
            ));
        }

        return new FastReviewListResponse(
                page,
                size,
                (int) stagePage.getTotalElements(),
                fastReviews
        );
    }
    public CompanyReviewListResponse getCompanyReviewList(final Long userId, final int page, final int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));

        // 지원별로 스케줄을 그룹화하여 페이징 처리
        Page<Schedule> schedulePage = scheduleRepository.findAllByUserUserId(userId, pageable);

        // 기업별로 리뷰를 분류하기 위한 리스트
        List<CompanyReviewDTO> companies = new ArrayList<>();

        // "서류"만 있는 스케줄을 제외하기 위한 리스트
        List<Schedule> filteredSchedules = new ArrayList<>();

        // 각 스케줄을 확인하여 "면접"이나 "중간"이 있는 경우만 필터링
        for (Schedule schedule : schedulePage) {
            List<Stage> stages = stageRepository.findAllBySchedule(schedule);

            // 스케줄의 스테이지 중 "면접"이나 "중간"이 있는지 확인
            boolean hasInterviewOrMidterm = stages.stream()
                    .anyMatch(stage -> stage.getType().getValue().equals("면접") || stage.getType().getValue().equals("중간"));

            // "면접" 또는 "중간" 스테이지가 있으면 필터링된 리스트에 추가
            if (hasInterviewOrMidterm) {
                filteredSchedules.add(schedule);
            }
        }

        // 필터링된 스케줄을 처리
        for (Schedule schedule : filteredSchedules) {
            String company = schedule.getCompany();
            String department = schedule.getDepartment();

            // 해당 스케줄의 모든 스테이지를 조회
            List<Stage> stages = stageRepository.findAllBySchedule(schedule);

            List<CompanyReviewDetailDTO> interviewReviews = new ArrayList<>();
            List<CompanyReviewDetailDTO> midtermReviews = new ArrayList<>();

            for (Stage stage : stages) {
                String type = stage.getType().getValue();
                boolean isReviewed = false;
                Long reviewId = null;

                if (type.equals("면접")) {
                    isReviewed = intReviewRepository.existsByStage(stage);
                    reviewId = isReviewed ? intReviewRepository.findIntReviewIdByStageAndUser(stage, user).orElse(null) : null;

                    // 면접 리뷰 추가
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
                    isReviewed = midReviewRepository.existsByStage(stage);
                    reviewId = isReviewed ? midReviewRepository.findMidReviewIdByStageAndUser(stage, user).orElse(null) : null;

                    // 중간 리뷰 추가
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

            // 회사별로 그룹화된 리뷰 DTO 추가
            companies.add(new CompanyReviewDTO(company, department, interviewReviews, midtermReviews));
        }

        // 총 페이지 수와 필터링된 스케줄에 따른 결과 반환
        return new CompanyReviewListResponse(
                page,
                size,
                (long) filteredSchedules.size(),
                companies
        );
    }

    @Transactional
    public void postSchedule(Long userId, SchedulePostRequest request) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));

        Schedule newSchedule = Schedule.toEntity(user, request);
        scheduleRepository.save(newSchedule);

        Stage newStage = Stage.toEntity(newSchedule, request);
        stageRepository.save(newStage);
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
}

