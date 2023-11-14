package handong.whynot.repository;

import handong.whynot.domain.BlindDateMatchingHelper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlindDateMatchingHelperRepository extends JpaRepository<BlindDateMatchingHelper, Long> {
  List<BlindDateMatchingHelper> findAllBySeason(Integer season);
}
