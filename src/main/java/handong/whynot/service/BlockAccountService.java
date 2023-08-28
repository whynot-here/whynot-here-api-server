package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.domain.BlockAccount;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.BlockAccountRequestDTO;
import handong.whynot.dto.account.BlockAccountResponseDTO;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.exception.account.BlockAccountExistException;
import handong.whynot.exception.account.BlockAccountNotFoundException;
import handong.whynot.repository.AccountRepository;
import handong.whynot.repository.BlockAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlockAccountService {

  private final AccountRepository accountRepository;
  private final BlockAccountRepository blockAccountRepository;

  @Transactional
  public void createBlockAccount(Account account, BlockAccountRequestDTO request) {
    // 차단할 사용자가 존재하는 사용자인지 확인
    accountRepository.findById(request.getAccountId())
      .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));
    if (Objects.equals(account.getId(), request.getAccountId())) {
      throw new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL);
    }

    // 이미 차단한 사용자 아닌지 확인
    List<BlockAccount> blockAccountList = blockAccountRepository.findAllByAccountId(account.getId());
    boolean isExist = blockAccountList.stream()
      .anyMatch(it -> Objects.equals(it.getBlockAccountId(), request.getAccountId()));
    if (isExist) {
      throw new BlockAccountExistException(AccountResponseCode.ALREADY_EXIST_BLOCK_ACCOUNT);
    }

    BlockAccount blockAccount = BlockAccount.builder()
      .accountId(account.getId())
      .blockAccountId(request.getAccountId())
      .build();
    blockAccountRepository.save(blockAccount);
  }


  @Transactional
  public void deleteBlockAccount(Account account, BlockAccountRequestDTO request) {
    List<BlockAccount> blockAccountList = blockAccountRepository.findAllByAccountId(account.getId());
    BlockAccount blockAccount = blockAccountList.stream()
      .filter(it -> Objects.equals(it.getBlockAccountId(), request.getAccountId()))
      .findFirst()
      .orElseThrow(() -> new BlockAccountNotFoundException(AccountResponseCode.BLOCK_ACCOUNT_READ_FAIL));

    blockAccountRepository.delete(blockAccount);
  }

  public List<BlockAccountResponseDTO> getAllBlockAccount(Account account) {

    return blockAccountRepository.findAllByAccountId(account.getId()).stream()
      .map(it -> {
        Account blockAccount = accountRepository.findById(it.getBlockAccountId())
          .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));
        return BlockAccountResponseDTO.of(blockAccount, it.getCreatedDt());
      })
      .collect(Collectors.toList());
  }
}
