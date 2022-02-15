package handong.whynot.service;

import handong.whynot.domain.Job;
import handong.whynot.dto.job.JobResponseCode;
import handong.whynot.exception.job.JobNotFoundException;
import handong.whynot.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public List<Job> getJobs() {

        return jobRepository.findAll();
    }

    public Job getJob(Long jobId) {

        return jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(JobResponseCode.JOB_READ_FAIL));
    }
}
