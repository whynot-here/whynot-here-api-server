package handong.whynot.dto.job;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobResponseDTO {

    private Long id;
    private String name;

}
