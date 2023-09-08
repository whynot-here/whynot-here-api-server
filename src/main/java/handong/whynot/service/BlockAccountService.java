package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.domain.BlockAccount;
import handong.whynot.domain.Post;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.BlockAccountCreateRequestDTO;
import handong.whynot.dto.account.BlockAccountResponseDTO;
import handong.whynot.dto.post.PostResponseCode;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.exception.account.BlockAccountExistException;
import handong.whynot.exception.account.BlockAccountNotFoundException;
import handong.whynot.exception.post.PostNotFoundException;
import handong.whynot.repository.AccountRepository;
import handong.whynot.repository.BlockAccountRepository;
import handong.whynot.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlockAccountService {

  private final AccountRepository accountRepository;
  private final BlockAccountRepository blockAccountRepository;
  private final PostRepository postRepository;

  @Transactional
  public void createBlockAccount(Account account, BlockAccountCreateRequestDTO request) {

    // 존재하는 post 인지 확인
    Post post = postRepository.findById(request.getPostId())
      .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

    // 차단할 사용자가 존재하는 사용자인지 확인
    Long accountId = post.getCreatedBy().getId();
    accountRepository.findById(accountId)
      .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));
    if (Objects.equals(account.getId(), accountId)) {
      throw new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL);
    }

    // 이미 차단한 사용자 아닌지 확인
    List<BlockAccount> blockAccountList = blockAccountRepository.findAllByAccountId(account.getId());
    boolean isExist = blockAccountList.stream()
      .anyMatch(it -> Objects.equals(it.getBlockAccountId(), accountId));
    if (isExist) {
      throw new BlockAccountExistException(AccountResponseCode.ALREADY_EXIST_BLOCK_ACCOUNT);
    }

    BlockAccount blockAccount = BlockAccount.builder()
      .accountId(account.getId())
      .blockAccountId(accountId)
      .build();
    blockAccountRepository.save(blockAccount);
  }


  @Transactional
  public void deleteBlockAccount(Account account, Long accountId) {
    List<BlockAccount> blockAccountList = blockAccountRepository.findAllByAccountId(account.getId());
    BlockAccount blockAccount = blockAccountList.stream()
      .filter(it -> Objects.equals(it.getBlockAccountId(), accountId))
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

  @Cacheable(value="BlockPost", key="'Account#' + #accountId + '-BlockPosts'")
  public List<Long> getAllBlockPostIds(Long accountId) {

    List<Long> accountIdList = blockAccountRepository.findAllByAccountId(accountId).stream()
      .map(BlockAccount::getBlockAccountId)
      .collect(Collectors.toList());

    List<Long> postIds = new ArrayList<>();
    for (Long id : accountIdList) {
      Optional<Account> findAccount = accountRepository.findById(id);
      findAccount.ifPresent(account -> postIds.addAll(postRepository.findAllByCreatedBy(account).stream()
        .map(Post::getId)
        .collect(Collectors.toList())));
    }

    return postIds;
  }
}
