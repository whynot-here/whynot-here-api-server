package handong.whynot.dto.user;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserResponseCode implements ResponseCode {
    USER_CREATE_OK(20001, "사용자 [생성]에 성공하였습니다."),
    USER_CREATE_FAIL_ALREADY_EXIST(40001, "사용자 [생성]에 실패하였습니다. - 이미 존재함"),

    USER_READ_OK(20002, "사용자 [조회]에 성공하였습니다."),
    USER_READ_FAIL(40002, "사용자 [조회]에 실패하였습니다."),

    USER_UPDATE_OK(20003, "사용자 [수정]에 성공하였습니다."),
    USER_UPDATE_FAIL_NOT_FOUND(40003, "사용자 [수정]에 실패하였습니다. - 해당 id 찾을 수 없음"),

    USER_DELETE_OK(20004, "사용자 [삭제]에 성공하였습니다."),
    USER_DELETE_FAIL_NOT_FOUND(40004, "사용자 [삭제]에 실패하였습니다. - 해당 id 찾을 수 없음");

    private final Integer statusCode;
    private final String message;
}
