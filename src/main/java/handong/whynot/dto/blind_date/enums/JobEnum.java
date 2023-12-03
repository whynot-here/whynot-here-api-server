package handong.whynot.dto.blind_date.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum JobEnum {
  PRIVATE("사기업 직장인")
  , PUBLIC("공무원/공공기관")
  , SPECIAL("특수직")
  , PROFESSIONAL("전문직")
  , FREE("프리랜서")
  , BUSINESS("사업자")
  , ETC("기타")
  , NOT_VALID("입력없음")
  ;

  private final String desc;

  public static JobEnum getJobEnum(String code) {
    return Arrays.stream(JobEnum.values())
      .filter(a -> a.name().equals(code)).findFirst().orElse(JobEnum.NOT_VALID);
  }
}
