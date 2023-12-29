package handong.whynot.dto.blind_date.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GBlindDateState {
  NO("참여X")
  , BLIND_ING("신청서 작성중")
  , SCREEN("내부 검수중")
  , FEE_ING("참여비 정보 제출중")
  , FEE("납부 확인중")
  , MATCH("1차 오픈 대기")
  , MATCH_OK("매칭 성공")
  , MATCH_FAIL("매칭 실패")
  , MATCH_REJECTED("상대방 재매칭 요구")
  , REMATCH("2차 오픈 대기")
  , REMATCH_OK("2차 매칭 완료")
  , FINISHED("시즌 종료")
  ;

  private final String desc;
}
