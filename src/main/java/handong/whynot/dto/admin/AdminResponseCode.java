package handong.whynot.dto.admin;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminResponseCode implements ResponseCode {
    ADMIN_USER_FEEDBACK_CREATE_OK(20001, "사용자 후기 [생성]에 성공하였습니다.");

    private final Integer statusCode;
    private final String message;
}
