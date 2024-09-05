package com.example.letscareer.stage.domain.repository;



import com.example.letscareer.stage.domain.model.Stage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

import static com.example.letscareer.stage.domain.model.QStage.stage;
@Repository
public class StageRepositoryImpl implements StageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StageRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Stage> findAllByUserIdAndMonth(Long userId, int month, Pageable pageable) {
        List<Stage> stages = queryFactory
                .selectFrom(stage)
                .where(
                        stage.schedule.user.userId.eq(userId),
                        stage.date.month().eq(month)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(stage)
                .where(
                        stage.schedule.user.userId.eq(userId),
                        stage.date.month().eq(month)
                )
                .fetchCount();

        return new PageImpl<>(stages, pageable, total);
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
}
