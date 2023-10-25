package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.domain.BlindDateFee;
import handong.whynot.domain.Role;
import handong.whynot.domain.StudentAuth;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.AdminApproveRequestDTO;
import handong.whynot.dto.account.StudentAuthRequestDTO;
import handong.whynot.dto.admin.AdminResponseCode;
import handong.whynot.dto.admin.AdminStudentAuthResponseDTO;
import handong.whynot.dto.mobile.CustomPushRequestDTO;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.exception.account.StudentAuthNotFoundException;
import handong.whynot.repository.AccountQueryRepository;
import handong.whynot.repository.AccountRepository;
import handong.whynot.repository.BlindDateFeeRepository;
import handong.whynot.repository.StudentAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final StudentAuthRepository studentAuthRepository;
  private final AccountRepository accountRepository;
  private final RoleService roleService;
  private final MobilePushService mobilePushService;
  private final AccountQueryRepository accountQueryRepository;
  private final BlindDateFeeRepository blindDateFeeRepository;

  @Transactional
  public void requestStudentAuth(StudentAuthRequestDTO dto, Account account) {

    StudentAuth studentAuth = StudentAuth.builder()
      .account(account)
      .imgUrl(dto.getImgUrl())
      .isAuthenticated(false)
      .build();

    studentAuthRepository.save(studentAuth);

    mobilePushService.pushAdminAuth(getAdminAccount(), getRequests(false).size());
  }

  @Transactional
  public void updateStudentAuth(StudentAuthRequestDTO dto, Account account) {
    StudentAuth studentAuth = studentAuthRepository.findByAccountId(account.getId())
      .orElseThrow(() -> new StudentAuthNotFoundException(AdminResponseCode.STUDENT_AUTH_NOT_FOUND));

    studentAuth.updateImageUrl(dto.getImgUrl());
  }

  public StudentAuth getStudentImg(Account account) {
    return studentAuthRepository.findByAccountId(account.getId()).orElse(null);
  }

  public List<AdminStudentAuthResponseDTO> getRequests(Boolean isAuthenticated) {

    return studentAuthRepository.findAllByIsAuthenticated(isAuthenticated).stream()
      .map(AdminStudentAuthResponseDTO::of)
      .collect(Collectors.toList());
  }

  @Transactional
  public void approveRequests(List<AdminApproveRequestDTO> approveList, Account approver) {
    List<Account> accountList = new ArrayList<>();

    for (AdminApproveRequestDTO approve: approveList) {
      StudentAuth studentAuth = studentAuthRepository.findByAccountId(approve.getAccountId())
        .orElseThrow(() -> new StudentAuthNotFoundException(AdminResponseCode.STUDENT_AUTH_NOT_FOUND));
      studentAuth.updateIsAuthenticated(approver.getEmail());

      Account account = accountRepository.findById(approve.getAccountId())
        .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));
      account.approveStudentAuth(approve.getStudentId(), approve.getStudentName());

      final Role role = roleService.getRoleByCode("ROLE_USER");
      account.addAccountRole(role);

      accountList.add(account);
    }

    mobilePushService.pushApproveStudentAuth(accountList);
  }

  public void deleteAuthHistory(Account account) {

    studentAuthRepository.deleteByAccount(account);
  }

  public List<Account> getAdminAccount() {
    return accountQueryRepository.getAdminAccountList();
  }

  public void sendCustomPush(CustomPushRequestDTO customPushRequestDTO) {
    List<Long> accountIds = customPushRequestDTO.getAccountIds();
    String url = customPushRequestDTO.getUrl();
    String title = customPushRequestDTO.getTitle();
    String body = customPushRequestDTO.getBody();

    List<Account> accountList = new ArrayList<>();
    for (Long id : accountIds) {
      Account account = accountRepository.findById(id)
        .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));
      accountList.add(account);
    }

    mobilePushService.pushCustomMessage(accountList, url, title, body);
  }

  @Transactional
  public void deleteBlindDateFee(Long accountId) {
    Account account = accountRepository.findById(accountId)
      .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));

    BlindDateFee blindDateFee = blindDateFeeRepository.findByAccountId(accountId);
    blindDateFee.deleteBlindDateFee();

    // 푸시 알림
    List<Account> accountList = Collections.singletonList(account);
    mobilePushService.pushDeleteBlindDateFee(accountList);
  }
}
