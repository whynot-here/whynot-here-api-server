package handong.whynot.repository;

import handong.whynot.domain.Accusation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccusationRepository extends JpaRepository<Accusation, Long> {
  List<Accusation> findAllByPostWriterId(Long postWriterId);
  List<Accusation> findAllByIsApproved(Boolean isApproved);
}
