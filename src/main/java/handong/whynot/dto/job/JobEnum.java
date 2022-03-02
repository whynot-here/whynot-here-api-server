package handong.whynot.dto.job;

import handong.whynot.domain.Job;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum JobEnum {
    DEVELOPER("developer", 1L),
    DESIGNER("designer", 2L),
    PROMOTER("promoter", 3L),
    ETC("etc", 4L);

    private final String jobName;
    private final Long code;

    public static List<Job> getJobInfoBy(List<JobEnum> jobEnumList) {

        return jobEnumList.stream()
                .map(jobEnum -> Job.builder()
                        .id(jobEnum.getCode())
                        .name(jobEnum.getJobName())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
