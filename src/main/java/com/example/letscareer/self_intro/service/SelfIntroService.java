package com.example.letscareer.self_intro.service;

import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.schedule.domain.Schedule;
import com.example.letscareer.schedule.repository.ScheduleRepository;
import com.example.letscareer.self_intro.domain.SelfIntro;
import com.example.letscareer.self_intro.dto.SelfIntroDTO;
import com.example.letscareer.self_intro.dto.request.SaveSelfIntroRequest;
import com.example.letscareer.self_intro.repository.SelfIntroRepository;
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
public class SelfIntroService {

    @Autowired
    private final SelfIntroRepository selfIntroRepository;
    private final ScheduleRepository scheduleRepository;
    private final StageRepository stageRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveSelfIntro(Long userId, Long scheduleId, Long stageId, SaveSelfIntroRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND_EXCEPTION));
        Stage stage = stageRepository.findByStageIdAndSchedule(stageId, schedule)
                .orElseThrow(() -> new NotFoundException(STAGE_NOT_FOUND_EXCEPTION));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EXCEPTION));

        for(SelfIntroDTO selfIntroDTO : request.selfIntros()) {
            SelfIntro selfIntro = SelfIntro.builder()
                    .title(selfIntroDTO.title())
                    .sequence(selfIntroDTO.sequence())
                    .content(selfIntroDTO.content())
                    .stage(stage)
                    .build();
            selfIntroRepository.save(selfIntro);
        }
    }
}
