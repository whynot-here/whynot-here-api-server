package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.domain.StudentAuth;
import handong.whynot.dto.account.AdminApproveRequestDTO;
import handong.whynot.dto.account.StudentAuthRequestDTO;
import handong.whynot.dto.account.StudentAuthResponseDTO;
import handong.whynot.dto.accusation.AccusationApproveRequestDTO;
import handong.whynot.dto.accusation.AccusationResponseCode;
import handong.whynot.dto.accusation.AccusationResponseDTO;
import handong.whynot.dto.admin.*;
import handong.whynot.dto.blind_date.BlindDateFeeResponseDTO;
import handong.whynot.dto.blind_date.BlindDateResponseCode;
import handong.whynot.dto.blind_date.MatchingRequestDTO;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.dto.mobile.CustomPushRequestDTO;
import handong.whynot.service.*;
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
    private final AccusationService accusationService;
    private final BlindDateService blindDateService;
    private final MatchingHistoryService matchingHistoryService;

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
        String authImg = null;
        Boolean isAuthenticated = false;
        if (auth != null) {
            authImg = auth.getImgUrl();
            isAuthenticated = auth.getIsAuthenticated();
        }

        return StudentAuthResponseDTO.builder()
          .accountId(account.getId())
          .imgUrl(authImg)
          .isAuthenticated(isAuthenticated)
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

        Account approver = accountService.getCurrentAccount();
        adminService.approveRequests(approveList, approver);

        return ResponseDTO.of(AdminResponseCode.ADMIN_APPROVE_REQUESTS_OK);
    }

    @Operation(summary = "신고 게시글 전체 조회")
    @GetMapping("/admin/accusation")
    public List<AccusationResponseDTO> getAllAccusation(@RequestParam("isApproved") Boolean isApproved) {
        return accusationService.getAllAccusation(isApproved);
    }

    @Operation(summary = "신고 게시글 신고 승인")
    @PutMapping("/admin/accusation")
    public ResponseDTO approveAccusation(@RequestBody AccusationApproveRequestDTO request) {
        accusationService.approveAccusation(request.getAccusationId(), request.getApproval());

        return ResponseDTO.of(AccusationResponseCode.ACCUSATION_SUBMIT_OK);
    }

    @Operation(summary = "남, 여 매칭 생성")
    @PostMapping("/admin/blind-matching")
    public ResponseDTO createMatching(@RequestBody MatchingRequestDTO request) {
        blindDateService.createMatching(request.getMaleId(), request.getFemaleId());

        return ResponseDTO.of(BlindDateResponseCode.MATCHING_CREATED_OK);
    }

    @Operation(summary = "매칭 리스트 조회")
    @GetMapping("/admin/blind-matching")
    public List<AdminBlindMatchingResponseDTO> getBlindMatchingListBySeason(@RequestParam Integer season) {
        return matchingHistoryService.getBlindMatchingListBySeason(season);
    }

    @Operation(summary = "신청자 리스트 조회")
    @GetMapping("/admin/blind-date")
    public List<AdminBlindDateResponseDTO> getBlindDateListBySeason(@RequestParam Integer season) {
        return blindDateService.getBlindDateListBySeason(season);
    }

    @Operation(summary = "매칭 삭제")
    @DeleteMapping("/admin/blind-matching/{matchingId}")
    public ResponseDTO deleteBlindMatching(@PathVariable Long matchingId) {
        matchingHistoryService.deleteBlindMatching(matchingId);

        return ResponseDTO.of(BlindDateResponseCode.MATCHING_DELETED_OK);
    }

    @Operation(summary = "매칭 상대방 정보 일괄 발송")
    @PostMapping("/admin/blind-matching/announce-partner-info")
    public ResponseDTO noticeMatchingInfoBySeason(@RequestParam Integer season) {
        blindDateService.noticeMatchingInfoBySeason(season);

        return ResponseDTO.of(BlindDateResponseCode.MATCHING_NOTICE_OK);
    }

    @Operation(summary = "관리자 수동 푸시")
    @PostMapping("/admin/custom-push")
    public ResponseDTO sendCustomPush(@RequestBody CustomPushRequestDTO customPushRequestDTO) {
        adminService.sendCustomPush(customPushRequestDTO);

        return ResponseDTO.of(AdminResponseCode.ADMIN_CUSTOM_PUSH_OK);
    }

    @Operation(summary = "보증금 납부 동의 삭제")
    @PutMapping("/admin/blind-date/delete-fee")
    public ResponseDTO deleteBlindDateFee(@RequestParam Long accountId) {
        adminService.deleteBlindDateFee(accountId);

        return ResponseDTO.of(AdminResponseCode.ADMIN_DELETE_BLIND_DATE_FEE_OK);
    }

    @Operation(summary = "관리자 보증금 납부 확인")
    @PutMapping("/admin/blind-date/fee/{feeId}")
    public ResponseDTO approveBlindDateFeeBySeason(@PathVariable Long feeId, @RequestParam Integer season) {
        adminService.approveBlindDateFee(feeId, season);

        return ResponseDTO.of(AdminResponseCode.ADMIN_APPROVE_BLIND_DATE_FEE_OK);
    }

    @Operation(summary = "보증금 납부 동의 리스트 조회")
    @GetMapping("/admin/blind-date/fee")
    public List<BlindDateFeeResponseDTO> getBlindDateFeeListBySeason(@RequestParam Integer season) {

        return adminService.getBlindDateFeeListBySeason(season);
    }
}
