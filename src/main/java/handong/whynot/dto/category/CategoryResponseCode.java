package handong.whynot.dto.category;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CategoryResponseCode implements ResponseCode {
    CATEGORY_CREATE_OK(20001, "카테고리 [생성]에 성공하였습니다."),
    CATEGORY_READ_FAIL(40001, "카테고리 [조회]에 실패하였습니다.");

    private final Integer statusCode;
    private final String message;
}
