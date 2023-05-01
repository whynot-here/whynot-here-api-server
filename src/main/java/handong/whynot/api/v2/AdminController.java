package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.domain.StudentAuth;
import handong.whynot.dto.account.AdminApproveRequestDTO;
import handong.whynot.dto.account.StudentAuthRequestDTO;
import handong.whynot.dto.account.StudentAuthResponseDTO;
import handong.whynot.dto.admin.AdminResponseCode;
import handong.whynot.dto.admin.AdminStudentAuthResponseDTO;
import handong.whynot.dto.admin.UserFeedbackRequestDTO;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.service.AccountService;
import handong.whynot.service.AdminService;
import handong.whynot.service.UserFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class AdminController {

    private final UserFeedbackService userFeedbackService;
    private final AccountService accountService;
    private final AdminService adminService;

    @Operation(summary = "사용자 후기 등록")
    @PostMapping("/admin/feedback")
    @ResponseStatus(CREATED)
    public ResponseDTO createUserFeedback(@RequestBody @Valid UserFeedbackRequestDTO request) {

        userFeedbackService.createUserFeedback(request);

        return ResponseDTO.of(AdminResponseCode.ADMIN_USER_FEEDBACK_CREATE_OK);
    }

    @Operation(summary = "학생증 인증 요청")
    @PostMapping("/student/request-auth")
    public ResponseDTO requestStudentAuth(@RequestBody StudentAuthRequestDTO dto) {

        Account account = accountService.getCurrentAccount();
        adminService.requestStudentAuth(dto, account);

        return ResponseDTO.of(AdminResponseCode.STUDENT_REQUEST_AUTH_OK);
    }

    @Operation(summary = "학생증 이미지 변경")
    @PutMapping("/student/request-auth")
    public ResponseDTO updateStudentAuth(@RequestBody StudentAuthRequestDTO dto) {

        Account account = accountService.getCurrentAccount();
        adminService.updateStudentAuth(dto, account);

        return ResponseDTO.of(AdminResponseCode.STUDENT_UPDATE_IMAGE_OK);
    }

    @Operation(summary = "본인 학생증 이미지 조회")
    @GetMapping("/student/img")
    public StudentAuthResponseDTO getStudentImg() {

        Account account = accountService.getCurrentAccount();
        StudentAuth auth = adminService.getStudentImg(account);

        return StudentAuthResponseDTO.builder()
          .accountId(account.getId())
          .imgUrl(auth.getImgUrl())
          .isAuthenticated(auth.getIsAuthenticated())
          .build();
    }

    @Operation(summary = "인증 요청 전체 조회")
    @GetMapping("/admin/requests")
    public List<AdminStudentAuthResponseDTO> getRequests(
      @RequestParam("isAuthenticated") Boolean isAuthenticated) {

        return adminService.getRequests(isAuthenticated);
    }

    @Operation(summary = "인증 요청 복수 승인")
    @PostMapping("/admin/requests")
    public ResponseDTO approveRequests(@RequestBody List<AdminApproveRequestDTO> approveList) {

        adminService.approveRequests(approveList);

        return ResponseDTO.of(AdminResponseCode.ADMIN_APPROVE_REQUESTS_OK);
    }
}
