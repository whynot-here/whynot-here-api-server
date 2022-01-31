package handong.whynot.dto.job;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JobResponseCode implements ResponseCode {
    JOB_CREATE_OK(20001, "직군 [생성]에 성공하였습니다."),
    JOB_CREATE_FAIL_ALREADY_EXIST(40001, "직군 [생성]에 실패하였습니다. - 이미 존재함"),

    JOB_READ_OK(20002, "직군 [조회]에 성공하였습니다."),
    JOB_READ_FAIL(40002, "직군 [조회]에 실패하였습니다."),

    JOB_UPDATE_OK(20003, "직군 [수정]에 성공하였습니다."),
    JOB_UPDATE_FAIL_NOT_FOUND(40003, "직군 [수정]에 실패하였습니다. - 해당 id 찾을 수 없음"),

    JOB_DELETE_OK(20004, "직군 [삭제]에 성공하였습니다."),
    JOB_DELETE_FAIL_NOT_FOUND(40004, "직군 [삭제]에 실패하였습니다. - 해당 id 찾을 수 없음");

    private final Integer statusCode;
    private final String message;
}
