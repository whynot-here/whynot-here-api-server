package handong.whynot.dto.comment;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentResponseCode implements ResponseCode {

    COMMENT_CREATE_OK(20001, "댓글 [생성]에 성공하였습니다."),
    COMMENT_DELETE_OK(20002, "댓글 [삭제]에 성공하였습니다."),


    COMMENT_DELETE_FAIL_NOT_FOUND(40001, "댓글 [삭제]에 실패하였습니다. - 해당 id 댓글 없음");

    private final Integer statusCode;
    private final String message;
}
