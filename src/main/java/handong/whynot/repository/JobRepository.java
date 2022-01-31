package handong.whynot.repository;

import handong.whynot.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    public Optional<Job> findById(Long id);
}
