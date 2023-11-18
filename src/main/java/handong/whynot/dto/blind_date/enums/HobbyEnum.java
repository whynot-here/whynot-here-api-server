package handong.whynot.dto.blind_date.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum HobbyEnum {
  READING_BOOKS("독서")
  , MUSIC("음악")
  , COOKING("요리")
  , GAME("게임")
  , SPORTS("스포츠")
  , HEALTH("헬스")
  , TRAVELING("여행")
  , DANCE("댄스")
  , DRAWING("그림")
  , BOARD_GAME("보드게임")
  , RUNNING("러닝")
  , NO("없음")
  , NOT_VALID("입력없음")
  ;

  private final String desc;

  public static HobbyEnum getHobbyEnum(String code) {
    return Arrays.stream(HobbyEnum.values())
      .filter(a -> a.name().equals(code)).findFirst().orElse(HobbyEnum.NOT_VALID);
  }
}
