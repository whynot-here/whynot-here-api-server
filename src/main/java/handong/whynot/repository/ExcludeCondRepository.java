package handong.whynot.repository;

import handong.whynot.domain.BlindDate;
import handong.whynot.domain.ExcludeCond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExcludeCondRepository extends JpaRepository<ExcludeCond, Long> {
  void deleteAllByBlindDate(BlindDate blindDate);

  List<ExcludeCond> findAllByBlindDate(BlindDate blindDate);
}
