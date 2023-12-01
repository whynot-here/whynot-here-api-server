package handong.whynot.dto.blind_date.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DateStyleEnum {
  CAFE("☕️ 카페")
  , PC("🕹️ pc방")
  , FOOD("🍖 맛집 탐방")
  , EXHIBITION("🎫 전시회 구경")
  , HEALTH("💪 헬스장 데이트")
  , ESCAPE_ROOM("🗝️ 방탈출")
  , WALK("👟 공원 산책")
  , DRIVE("🚙 드라이브")
  , INSIDE("🏠 실내")
  , OUTSIDE("🏞️ 실외")
  , NOT_VALID("입력없음")
  ;

  private final String desc;

  public static DateStyleEnum getDateStyleEnum(String code) {
    return Arrays.stream(DateStyleEnum.values())
      .filter(a -> a.name().equals(code)).findFirst().orElse(DateStyleEnum.NOT_VALID);
  }
}
