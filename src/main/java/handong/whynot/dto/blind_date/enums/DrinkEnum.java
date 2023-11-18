package handong.whynot.dto.blind_date.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DrinkEnum {
  SOMETIMES("가끔")
  , ONETWO_OF_WEEK("일주일에 1-2번")
  , FIVE_OF_WEEK("일주일에 5번 이상")
  , NEVER("안마심")
  , NOT_VALID("입력없음")
  ;

  private final String desc;

  public static DrinkEnum getDrinkEnum(String code) {
    return Arrays.stream(DrinkEnum.values())
      .filter(a -> a.name().equals(code)).findFirst().orElse(DrinkEnum.NOT_VALID);
  }
}
