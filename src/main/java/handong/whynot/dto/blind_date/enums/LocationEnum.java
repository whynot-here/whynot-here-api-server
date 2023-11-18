package handong.whynot.dto.blind_date.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum LocationEnum {
  DORMITORY("기숙사")
  , POHANG("포항")
  , ETC("그 외 지역")
  , NOT_VALID("입력없음")
  ;

  private final String desc;

  public static LocationEnum getLocationEnum(String code) {
    return Arrays.stream(LocationEnum.values())
      .filter(a -> a.name().equals(code)).findFirst().orElse(LocationEnum.NOT_VALID);
  }
}
