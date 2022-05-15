package handong.whynot.dto.cloud;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum S3ResponseCode implements ResponseCode {
    AWS_S3_UPLOAD_OK(20001, "AWS S3 [업로드]에 성공하였습니다."),
    AWS_S3_UPLOAD_FAIL(40001, "AWS S3 [업로드]에 실패하였습니다."),
    AWS_S3_UPLOAD_FAIL_INVALID_TYPE(40002, "파일 형식이 올바르지 않습니다."),
    AWS_S3_UPLOAD_FAIL_ACL_ISSUE(40003, "AWS S3 ACL설정이 올바르지 않습니다.");

    private final Integer statusCode;
    private final String message;
}