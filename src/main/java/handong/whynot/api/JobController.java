package handong.whynot.api;

import handong.whynot.domain.Job;
import handong.whynot.dto.job.JobResponseDTO;
import handong.whynot.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("")
    public List<JobResponseDTO> getJobs() {

        return jobService.getJobs().stream()
                .map(job -> JobResponseDTO.builder()
                        .id(job.getId())
                        .name(job.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{jobId}")
    public JobResponseDTO getJob(@PathVariable Long jobId) {

        Job job = jobService.getJob(jobId);
        return JobResponseDTO.builder()
                .id(job.getId())
                .name(job.getName())
                .build();
    }
}
