package handong.whynot.dto.job;

import handong.whynot.domain.Job;
import handong.whynot.dto.post.PostStatus;
import handong.whynot.exception.job.JobNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter
@RequiredArgsConstructor
public enum JobEnum {
    DEVELOPER("developer", 1),
    DESIGNER("designer", 2),
    PROMOTER("promoter", 3),
    ETC("etc", 4);

    private final String jobName;
    private final Integer code;

    public static List<Job> getJobInfoBy(String jobStr) {

        List<Job> jobList = new ArrayList<Job>();

        try{
            String[] jobs = jobStr.split(",");
            for(String job: jobs) {
                JobEnum jobEnum = findBy(job)
                        .orElseThrow(() -> new JobNotFoundException(JobResponseCode.JOB_READ_FAIL));

            }

            return Arrays.asList();
        } catch (Exception e) {
            return jobList;
        }
    }

    private static Optional<JobEnum> findBy(String job) {

        if (Arrays.stream(JobEnum.values())
                .anyMatch(it -> it.getJobName().equals(job))) {
            return Optional.of(JobEnum.valueOf(job.toUpperCase()));
        }
        return Optional.empty();
    }
}
