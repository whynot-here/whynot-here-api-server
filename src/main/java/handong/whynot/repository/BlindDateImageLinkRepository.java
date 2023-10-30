package handong.whynot.repository;

import handong.whynot.domain.BlindDate;
import handong.whynot.domain.BlindDateImageLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlindDateImageLinkRepository extends JpaRepository<BlindDateImageLink, Long> {
  void deleteAllByBlindDate(BlindDate blindDate);
}
