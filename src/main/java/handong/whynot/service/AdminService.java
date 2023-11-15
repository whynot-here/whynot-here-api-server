package handong.whynot.service;

import handong.whynot.domain.*;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.AdminApproveRequestDTO;
import handong.whynot.dto.account.StudentAuthRequestDTO;
import handong.whynot.dto.admin.AdminResponseCode;
import handong.whynot.dto.admin.AdminStudentAuthResponseDTO;
import handong.whynot.dto.blind_date.BlindDateFeeResponseDTO;
import handong.whynot.dto.blind_date.BlindDateResponseCode;
import handong.whynot.dto.mobile.CustomPushRequestDTO;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.exception.account.StudentAuthNotFoundException;
import handong.whynot.exception.blind_date.BlindDateFeeNotFoundException;
import handong.whynot.exception.blind_date.BlindDateNotFoundException;
import handong.whynot.exception.blind_date.MatchingNotFoundException;
import handong.whynot.repository.*;
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
  private final BlindDateService blindDateService;
  private final MatchingHistoryRepository matchingHistoryRepository;
  private final BlindDateRepository blindDateRepository;

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

  @Transactional
  public void approveBlindDateFee(Long feeId, Integer season, Account approver) {
    // 1. is_submitted true 업데이트
    BlindDateFee blindDateFee = blindDateFeeRepository.findById(feeId)
      .orElseThrow(() -> new BlindDateFeeNotFoundException(BlindDateResponseCode.BLIND_DATE_FEE_READ_FAIL));

    blindDateFee.approveBlindDateFee(approver.getEmail());

    // 2. blind_date 생성
    Account account = accountRepository.findById(blindDateFee.getAccountId())
      .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));

    blindDateService.createBlindDate(season, account);

    // 3. 푸시 알림
    List<Account> accountList = Collections.singletonList(account);
    mobilePushService.pushApproveBlindDateFee(accountList);
  }

  public List<BlindDateFeeResponseDTO> getBlindDateFeeListBySeason(Integer season) {

    return blindDateFeeRepository.findAllBySeason(season).stream()
      .map(BlindDateFeeResponseDTO::of)
      .collect(Collectors.toList());
  }

  @Transactional
  public void approveMatchingImage(Long matchingId, Account account) {
    MatchingHistory matchingHistory = matchingHistoryRepository.findById(matchingId)
      .orElseThrow(() -> new MatchingNotFoundException(BlindDateResponseCode.MATCHING_READ_FAIL));

    matchingHistory.setIsApproved(true);
    matchingHistory.setApprover(account.getEmail());

    // 푸시 알림
    List<Account> accountList = new ArrayList<>();
    BlindDate male = blindDateRepository.findById(matchingHistory.getMaleId())
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));
    BlindDate female = blindDateRepository.findById(matchingHistory.getFemaleId())
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    accountList.add(male.getAccount());
    accountList.add(female.getAccount());
    mobilePushService.pushApproveMatchingImage(accountList);
  }
}
