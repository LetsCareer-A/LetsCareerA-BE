package com.example.letscareer.stage.domain.repository;


import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.stage.domain.model.QStage;
import com.example.letscareer.stage.domain.model.Stage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.example.letscareer.int_review.domain.model.QIntReview.intReview;
import static com.example.letscareer.mid_review.domain.model.QMidReview.midReview;
import static com.example.letscareer.stage.domain.model.QStage.stage;

@Repository
public class StageRepositoryImpl implements StageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StageRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Stage> findAllByUserIdAndMonth(Long userId, int month, Pageable pageable) {
        List<Stage> stages = getStagesByUserIdAndMonth(userId, month, pageable);
        long total = getTotalCountByUserIdAndMonth(userId, month);

        return new PageImpl<>(stages, pageable, total);
    }

    private List<Stage> getStagesByUserIdAndMonth(Long userId, int month, Pageable pageable) {
        return queryFactory
                .selectFrom(stage)
                .where(
                        stage.schedule.user.userId.eq(userId),
                        stage.date.month().eq(month)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private long getTotalCountByUserIdAndMonth(Long userId, int month) {
        return queryFactory
                .selectFrom(stage)
                .where(
                        stage.schedule.user.userId.eq(userId),
                        stage.date.month().eq(month)
                )
                .fetchCount();
    }

    @Override
    public List<Stage> findAllByUserIdAndDate(Long userId, LocalDate date) {
        return queryFactory
                .selectFrom(stage)
                .where(
                        stage.schedule.user.userId.eq(userId),
                        stage.date.eq(date)
                )
                .fetch();
    }

    @Override
    public List<Stage> findAllByUserIdAndMonth(Long userId, int month) {
        return queryFactory
                .selectFrom(stage)
                .where(
                        stage.schedule.user.userId.eq(userId),
                        stage.date.month().eq(month)
                )
                .fetch();
    }


    @Override
    public Page<Stage> findAllByScheduleInAndDateBetweenAndIntReviewNotExistsAndMidReviewNotExists(
            List<Schedule> schedules, LocalDate startDate, LocalDate endDate, Pageable pageable) {

        List<Stage> stages = getStagesWithoutReviews(schedules, startDate, endDate, pageable);
        long total = getCountStagesWithoutReviews(schedules, startDate, endDate);

        return new PageImpl<>(stages, pageable, total);
    }

    private List<Stage> getStagesWithoutReviews(List<Schedule> schedules, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return queryFactory
                .selectFrom(stage)
                .where(
                        stage.schedule.in(schedules),
                        stage.date.between(startDate, endDate),
                        notExistsIntReview(stage),
                        notExistsMidReview(stage)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private long getCountStagesWithoutReviews(List<Schedule> schedules, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .selectFrom(stage)
                .where(
                        stage.schedule.in(schedules),
                        stage.date.between(startDate, endDate),
                        notExistsIntReview(stage),
                        notExistsMidReview(stage)
                )
                .fetchCount();
    }

    // Helper methods to check if IntReview or MidReview exists
    private BooleanExpression notExistsIntReview(QStage stage) {
        return JPAExpressions.selectFrom(intReview)
                .where(intReview.stage.eq(stage))
                .notExists();
    }

    private BooleanExpression notExistsMidReview(QStage stage) {
        return JPAExpressions.selectFrom(midReview)
                .where(midReview.stage.eq(stage))
                .notExists();
    }
}
