package handong.whynot.service;

import handong.whynot.domain.Role;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.exception.account.RoleNotFoundException;
import handong.whynot.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;

  public Role getRoleByCode(String code) {

    return roleRepository.findByCode(code)
      .orElseThrow(() -> new RoleNotFoundException(AccountResponseCode.ROLE_READ_FAIL));
  }
}
