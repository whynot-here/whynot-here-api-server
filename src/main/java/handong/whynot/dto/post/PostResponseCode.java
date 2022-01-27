package handong.whynot.dto.post;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum PostResponseCode implements ResponseCode {
    POST_CREATE_OK(200, "공고 [생성]에 성공하였습니다."),
    POST_CREATE_FAIL_ALREADY_EXIST(400, "공고 [생성]에 실패하였습니다. - 이미 존재함"),

    POST_READ_OK(200, "공고 [조회]에 성공하였습니다."),
    POST_READ_FAIL(400, "공고 [조회]에 실패하였습니다."),

    POST_UPDATE_OK(200, "공고 [수정]에 성공하였습니다."),
    POST_UPDATE_FAIL_NOT_FOUND(400, "공고 [수정]에 실패하였습니다. - 해당 id 찾을 수 없음"),

    POST_DELETE_OK(200, "공고 [삭제]에 성공하였습니다."),
    POST_DELETE_FAIL_NOT_FOUND(400, "공고 [삭제]에 실패하였습니다. - 해당 id 찾을 수 없음");

    private final Integer statusCode;
    private final String message;
}
