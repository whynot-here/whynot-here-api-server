package handong.whynot.dto.admin;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminResponseCode implements ResponseCode {
    ADMIN_USER_FEEDBACK_CREATE_OK(20001, "사용자 후기 [생성]에 성공하였습니다."),
    STUDENT_REQUEST_AUTH_OK(20002, "학생증 요청 [생성]에 성공하였습니다."),
    STUDENT_UPDATE_IMAGE_OK(20003, "학생증 이미지 [수정]에 성공하였습니다."),
    STUDENT_DELETE_OK(20004, "학생증 권한 [삭제]에 성공하였습니다."),
    ADMIN_APPROVE_REQUESTS_OK(20004, "인증 요청 [승인]에 성공하였습니다."),
    ADMIN_CUSTOM_PUSH_OK(20005, "관리자 수동 메세지 발송에 성공하였습니다."),
    ADMIN_DELETE_BLIND_DATE_FEE_OK(20006, "보증금 납부 내역 삭제에 성공하였습니다."),
    ADMIN_APPROVE_BLIND_DATE_FEE_OK(20007, "보증금 납부를 관리자가 확인하였습니다."),
    ADMIN_APPROVE_MATCHING_IMAGE_OK(20008, "만남 인증을 관리자가 승인하였습니다."),
    ADMIN_BASE_MATCHING_OK(20009, "기본 조건 매칭을 업데이트하였습니다."),
    ADMIN_SCREEN_OK(20010, "내부 검수를 완료하였습니다."),
    NOT_VALID_CONSTRAINT(40001, "[입력조건] 검증에 실패하였습니다."),
    STUDENT_AUTH_NOT_FOUND(40002, "학생증 요청 [조회]에 실패하였습니다."),
    NOT_VALID_STUDENT_TYPE(40003, "졸업생 인증 완료 대상자로, 학생증 초기화 불가합니다.");

    private final Integer statusCode;
    private final String message;
}
