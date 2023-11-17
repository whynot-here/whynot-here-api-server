package handong.whynot.repository;

import handong.whynot.domain.BlindDate;
import handong.whynot.domain.BlindDateImageLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlindDateImageLinkRepository extends JpaRepository<BlindDateImageLink, Long> {
  void deleteAllByBlindDate(BlindDate blindDate);

  List<BlindDateImageLink> findAllByBlindDate(BlindDate blindDate);
}
