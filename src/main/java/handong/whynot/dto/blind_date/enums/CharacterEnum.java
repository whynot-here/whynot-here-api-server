package handong.whynot.dto.blind_date.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CharacterEnum {
  PRETTY_TALKING("말을 예쁘게 하는")
  , HUMOROUS("유머러스 한")
  , PLAYFUL("장난기 많은")
  , POLITE("예의 바른")
  , CAREFUL("진중한")
  , GOOD_MATCH("상대에게 잘 맞추는")
  , THOUGHTFUL("배려심 깊은")
  , NOT_VALID("입력없음")
  ;

  private final String desc;

  public static CharacterEnum getCharacterEnum(String code) {
    return Arrays.stream(CharacterEnum.values())
      .filter(a -> a.name().equals(code)).findFirst().orElse(CharacterEnum.NOT_VALID);
  }
}
