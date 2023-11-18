package handong.whynot.dto.blind_date.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DepartmentEnum {
  GLOBAL("글로벌리더쉽")
  , INTERNATIONAL("국제어문")
  , ECONOMICS("경영경제")
  , LAW("법학부")
  , COMMUNICATION("커뮤니케이션")
  , PSYCHOLOGY("상담복지")
  , BIO("생명과학")
  , ENVIRONMENT("공간환경시스템")
  , COMPUTER("전산전자")
  , CONTENTS("콘텐츠융합디자인")
  , MECHANICS("기계제어")
  , ICT("ICT창업학부")
  , LANGUAGE("언어교육원")
  , CREATIVE("창의융합교육원")
  , AI("AI융합교육원")
  , NOT_VALID("입력없음")
  ;

  private final String desc;

  public static DepartmentEnum getDepartmentEnum(String code) {
    return Arrays.stream(DepartmentEnum.values())
      .filter(a -> a.name().equals(code)).findFirst().orElse(DepartmentEnum.NOT_VALID);
  }
}
