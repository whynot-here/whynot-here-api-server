package handong.whynot.dto.blind_date.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ContactStyleEnum {
  KAKAO_OFTEN("카톡 자주 하는 편")
  , KAKAO_RARELY("카톡 잘 못하는 편")
  , MEET("직접 만나는 것을 선호")
  , CALL("전화 선호")
  , NOT_VALID("입력없음")
  ;

  private final String desc;

  public static ContactStyleEnum getContactStyleEnum(String code) {
    return Arrays.stream(ContactStyleEnum.values())
      .filter(a -> a.name().equals(code)).findFirst().orElse(ContactStyleEnum.NOT_VALID);
  }
}
