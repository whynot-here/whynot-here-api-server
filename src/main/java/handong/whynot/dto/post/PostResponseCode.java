package handong.whynot.dto.post;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum PostResponseCode implements ResponseCode {
    POST_CREATE_OK(20001, "공고 [생성]에 성공하였습니다."),
    POST_CREATE_FAIL(40001, "공고 [생성]에 실패하였습니다."),

    POST_READ_OK(20002, "공고 [조회]에 성공하였습니다."),
    POST_READ_FAIL(40002, "공고 [조회]에 실패하였습니다."),

    POST_UPDATE_OK(20003, "공고 [수정]에 성공하였습니다."),
    POST_UPDATE_FAIL_NOT_FOUND(40003, "공고 [수정]에 실패하였습니다. - 해당 id 찾을 수 없음"),

    POST_DELETE_OK(20004, "공고 [삭제]에 성공하였습니다."),
    POST_DELETE_FAIL_NOT_FOUND(40004, "공고 [삭제]에 실패하였습니다. - 해당 id 찾을 수 없음"),

    POST_CREATE_FAVORITE_OK(20005, "좋아요 공고 [생성]에 성공하였습니다."),
    POST_CREATE_FAVORITE_FAIL(40005, "좋아요 공고 [생성]에 실패하였습니다. - 이미 좋아요 눌렀습니다."),
      
    POST_DELETE_FAVORITE_OK(20006, "좋아요 공고 [삭제]에 성공하였습니다."),
    POST_DELETE_FAVORITE_FAIL(40006, "좋아요 공고 [삭제]에 실패하였습니다. - 이미 좋아요 해제되어 있습니다."),

    POST_CREATE_APPLY_OK(20009, "신청한 공고 [생성]에 성공하였습니다."),
    POST_CREATE_APPLY_FAIL(40009, "신청한 공고 [생성]에 실패하였습니다. - 이미 신청한 공고입니다."),
  
    POST_DELETE_APPLY_OK(20010, "신청한 공고 [삭제]에 성공하였습니다."),
    POST_DELETE_APPLY_FAIL(40010, "신청한 공고 [삭제]에 실패하였습니다. - 이미 취소 신청하였습니다."),

    POST_INVALID_QUERY_STRING(40011, "입력값이 올바르지 않습니다."),

    POST_END_RECRUIT_OK(20011, "공고 모집상태 변경 완료되었습니다.");

    private final Integer statusCode;
    private final String message;
}
