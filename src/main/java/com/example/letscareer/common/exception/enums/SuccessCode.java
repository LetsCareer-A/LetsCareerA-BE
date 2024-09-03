package com.example.letscareer.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {


    SCHEDULE_SUCCESS(HttpStatus.OK, "일정 찾기 성공입니다."),
    POST_SCHEDULE_SUCCESS(HttpStatus.OK, "새 일정 추가 성공입니다."),
    FAST_REVIEW_LIST_SUCEESS(HttpStatus.OK, "빠른 회고 리스트 찾기 성공입니다"),
    REVIEW_LIST_SUCCESS(HttpStatus.OK,"회고 리스트 찾기 성공입니다"),
    TODO_SUCCESS(HttpStatus.OK, "투두 찾기 성공입니다."),
    TODO_SAVE_SUCCESS(HttpStatus.OK, "투두 저장 성공입니다"),
    TODO_DELETE_SUCCESS(HttpStatus.OK, "투두 삭제 성공입니다"),
    TODO_TOGGLE_SUCCESS(HttpStatus.OK, "투두 토글 변경 성공입니다"),
    SAVE_CAREER_SUCCESS(HttpStatus.CREATED, "커리어 등록 성공"),
    GET_CAREER_DETAIL_SUCCESS(HttpStatus.OK, "커리어 상세 조회 성공"),
    MID_REVIEW_GET_SUCCESS(HttpStatus.CREATED, "중간 전형 회고 가져오기 성공"),
    INT_REVIEW_GET_SUCCESS(HttpStatus.CREATED, "면접 전형 회고 가져오기 성공"),
    MID_REVIEW_SAVE_SUCCESS(HttpStatus.CREATED, "중간 전형 회고 추가 성공"),
    INT_REVIEW_SAVE_SUCCESS(HttpStatus.CREATED, "면접 회고 추가 성공"),
    SELF_INTRO_SAVE_SUCCESS(HttpStatus.CREATED, "자기소개 추가 성공"),
    APPEAL_CAREERS_ADD_SUCCESS(HttpStatus.CREATED, "어필할 커리어 추가 성공"),
    STAGE_ADD_SUCCESS(HttpStatus.CREATED, "전형 단계 추가 성공"),
    STAGE_UPDATE_SUCCESS(HttpStatus.OK, "전형 단계 상태 변경 성공"),
    UPDATE_SCHEDULE_PROGRESS_SUCCESS(HttpStatus.OK, "일정 진행 상태 변경 성공"),
    STAGES_GET_SUCCESS(HttpStatus.OK, "지원 일정 및 단계 조회 성공"),
    DOC_STAGES_GET_SUCCESS(HttpStatus.OK, "서류전형 단계 조회 성공"),
    MID_STAGES_GET_SUCCESS(HttpStatus.OK, "중간전형 단계 조회 성공"),;

    private final HttpStatus httpStatus;
    private final String message;
}
