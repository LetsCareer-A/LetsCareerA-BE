package com.example.letscareer.always.service;

import com.example.letscareer.always.domain.Always;
import com.example.letscareer.always.dto.AlwaysDTO;
import com.example.letscareer.always.dto.response.AlwaysResponse;
import com.example.letscareer.always.repository.AlwaysRepository;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.stage.domain.Stage;
import com.example.letscareer.stage.repository.StageRepository;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.letscareer.common.exception.enums.ErrorCode.USER_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class AlwaysService {
    private final UserRepository userRepository;
    private final AlwaysRepository alwaysRepository;
    private final StageRepository stageRepository;


    public AlwaysResponse getAlwaysList(final Long userId, final int page, final int size) {
        // 사용자 존재 여부 확인
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND_EXCEPTION);
        }

        // 페이지네이션 설정
        Pageable pageable = PageRequest.of(page - 1, size);

        // 사용자 ID로 항상 일정 목록 조회
        Page<Always> alwaysPage = alwaysRepository.findAllByUserUserId(userId, pageable);

        // 항상 일정 목록을 DTO로 변환
        List<AlwaysDTO> alwaysList = new ArrayList<>();
        for (Always always : alwaysPage) {
            Long alwaysId = always.getAlwaysId();
            String company = always.getCompany();
            String department = always.getDepartment();


            // 관련된 Stage 정보 가져오기 (최대 order 기준)
            Optional<Stage> stageOptional = stageRepository.findTopByAlwaysAlwaysIdOrderByOrderDesc(alwaysId);
            Long stageId = stageOptional.map(Stage::getStageId).orElse(null);
            String status = stageOptional.map(Stage::getStatus).get().getValue();

            // DTO로 변환하여 리스트에 추가
            alwaysList.add(new AlwaysDTO(alwaysId, stageId, company, department, status));
        }

        return new AlwaysResponse(page, size, alwaysList);
    }
}
