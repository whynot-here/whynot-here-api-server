package handong.whynot.dto.job;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class JobDTO {

    private boolean developer;
    private boolean designer;
    private boolean promoter;
    private boolean etc;

}