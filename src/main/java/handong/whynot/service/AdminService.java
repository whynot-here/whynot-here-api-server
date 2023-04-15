package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.domain.StudentAuth;
import handong.whynot.dto.account.StudentAuthRequestDTO;
import handong.whynot.dto.admin.AdminResponseCode;
import handong.whynot.exception.account.StudentAuthNotFoundException;
import handong.whynot.repository.StudentAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final StudentAuthRepository studentAuthRepository;

  public void requestStudentAuth(StudentAuthRequestDTO dto, Account account) {

    StudentAuth studentAuth = StudentAuth.builder()
      .account(account)
      .imgUrl(dto.getImgUrl())
      .isAuthenticated(false)
      .build();

    studentAuthRepository.save(studentAuth);
  }

  @Transactional
  public void updateStudentAuth(StudentAuthRequestDTO dto, Account account) {
    StudentAuth studentAuth = studentAuthRepository.findByAccountId(account.getId())
      .orElseThrow(() -> new StudentAuthNotFoundException(AdminResponseCode.STUDENT_AUTH_NOT_FOUND));

    studentAuth.updateImageUrl(dto.getImgUrl());
  }
}
