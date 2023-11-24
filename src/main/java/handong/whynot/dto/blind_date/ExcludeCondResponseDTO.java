package handong.whynot.dto.blind_date;

import handong.whynot.domain.ExcludeCond;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExcludeCondResponseDTO {
  private String name;
  private String department;
  private String studentId;

  public static ExcludeCondResponseDTO of(ExcludeCond excludeCond) {
    return builder()
      .name(excludeCond.getName())
      .department(excludeCond.getDepartment())
      .studentId(excludeCond.getStudentId())
      .build();
  }
}
