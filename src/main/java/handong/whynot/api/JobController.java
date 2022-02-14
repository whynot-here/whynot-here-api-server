package handong.whynot.api;

import handong.whynot.domain.Job;
import handong.whynot.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("")
    public List<Job> getJobs() {
        return jobService.getJobs();
    }

    @GetMapping("/{jobId}")
    public Job getJob(@PathVariable Long jobId) {
        return jobService.getJob(jobId);
    }
}
