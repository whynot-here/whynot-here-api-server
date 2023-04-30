package handong.whynot.repository;

import handong.whynot.domain.StudentAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentAuthRepository extends JpaRepository<StudentAuth, Long> {
  Optional<StudentAuth> findByAccountId(Long id);

  List<StudentAuth> findAllByIsAuthenticated(Boolean isAuthenticated);
}
