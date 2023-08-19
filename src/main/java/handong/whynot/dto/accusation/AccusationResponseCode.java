package handong.whynot.dto.accusation;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccusationResponseCode implements ResponseCode {
  ACCUSATION_CREATED_OK(20001, "게시글 신고에 성공하였습니다."),
  ACCUSATION_SUBMIT_OK(20002, "게시글 신고에 대한 응답에 성공하였습니다."),
  ACCUSATION_READ_FAIL(40001, "신고 [조회]에 실패하였습니다.");

  private final Integer statusCode;
  private final String message;
}
