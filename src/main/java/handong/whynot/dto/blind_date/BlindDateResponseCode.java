package handong.whynot.dto.blind_date;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlindDateResponseCode implements ResponseCode {
  BLIND_DATE_CREATED_OK(20001, "소개팅 지원에 성공하였습니다."),
  BLIND_DATE_CREATED_FAIL(40001, "소개팅 지원에 실패하였습니다."),
  BLIND_DATE_READ_FAIL(40002, "소개팅 [조회]에 실패하였습니다."),
  BLIND_DATE_NOT_AUTHENTICATED(40003, "학생증 인증이 되지 않은 사용자입니다."),
  BLIND_DATE_DUPLICATED(40004, "이번 시즌에 소개팅 지원한 이력이 있습니다."),
  BLIND_DATE_NOT_MATCHED(40005, "매칭된 대상이 없습니다.");

  private final Integer statusCode;
  private final String message;
}
