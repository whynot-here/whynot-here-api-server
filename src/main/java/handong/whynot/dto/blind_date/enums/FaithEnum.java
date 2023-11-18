package handong.whynot.dto.blind_date.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum FaithEnum {
  CHRISTIAN("기독교")
  , NOTHING("무교")
  , ETC("그 외")
  , NOT_VALID("입력없음")
  ;

  private final String desc;

  public static FaithEnum getFaithEnum(String code) {
    return Arrays.stream(FaithEnum.values())
      .filter(a -> a.name().equals(code)).findFirst().orElse(FaithEnum.NOT_VALID);
  }
}
