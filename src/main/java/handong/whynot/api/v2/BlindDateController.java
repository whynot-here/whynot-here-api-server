package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.domain.BlindDateSummary;
import handong.whynot.dto.blind_date.*;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.service.AccountService;
import handong.whynot.service.BlindDateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/blind-date")
public class BlindDateController {

  private final BlindDateService blindDateService;
  private final AccountService accountService;

  @Operation(summary = "소개팅 지원")
  @PostMapping("")
  @ResponseStatus(CREATED)
  public void createBlindDate(@RequestBody BlindDateRequestDTO request) {

//    Account account = accountService.getCurrentAccount();
//    blindDateService.createBlindDate(request, account);
//
//    return ResponseDTO.of(BlindDateResponseCode.BLIND_DATE_CREATED_OK);
  }

  @Operation(summary = "소개팅 수정")
  @PutMapping("")
  public ResponseDTO updateBlindDate(@RequestBody BlindDateRequestDTO request) {
    Account account = accountService.getCurrentAccount();
    blindDateService.updateBlindDate(request, account);

    return ResponseDTO.of(BlindDateResponseCode.BLIND_DATE_UPDATED_OK);
  }


  @Operation(summary = "소개팅 지원자 카운트 조회")
  @GetMapping("/total-cnt")
  public Long getApplicantsCntBySeason(@RequestParam Integer season) {
    return blindDateService.getApplicantsCntBySeason(season);
  }

  @Operation(summary = "소개팅 지원 여부 조회")
  @GetMapping("/participation")
  public Boolean getIsParticipatedBySeason(@RequestParam Integer season) {
    Account account = accountService.getCurrentAccount();
    return blindDateService.getIsParticipatedBySeason(season, account);
  }

  @Operation(summary = "매칭 결과 노출 여부 조회")
  @GetMapping("/reveal-result")
  public Boolean getIsRevealResultBySeason(@RequestParam Integer season) {
    Account account = accountService.getCurrentAccount();
    return blindDateService.getIsRevealResultBySeason(season, account);
  }

  @Operation(summary = "매칭 후 상대방 정보 조회")
  @GetMapping("/matching-result")
  public BlindDateResponseDTO getMatchingResultBySeason(@RequestParam Integer season) {
    Account account = accountService.getCurrentAccount();
    return blindDateService.getMatchingResultBySeason(season, account);
  }

  @Operation(summary = "매칭 승인 or 거절")
  @CacheEvict(value="MatchedAccountList", key="'MatchedAccountList'")
  @PostMapping("/apply")
  public ResponseDTO submitApply(@RequestBody BlindDateApplyDTO applyDTO) {
    Account account = accountService.getCurrentAccount();
    blindDateService.submitApply(applyDTO.getApproval(), applyDTO.getSeason(), account);
    return ResponseDTO.of(BlindDateResponseCode.BLIND_DATE_SUBMIT_APPLY_OK);
  }

  @Operation(summary = "매칭 결과 summary 조회")
  @GetMapping("/summary")
  public BlindDateSummary getMatchingResultSummary(@RequestParam Integer season) {

    return blindDateService.getMatchingResultSummary(season);
  }

  @Operation(summary = "보증금 납부 동의")
  @PostMapping("/fee")
  public ResponseDTO createBlindDateFee(@RequestBody BlindDateFeeRequestDTO dto) {
    Account account = accountService.getCurrentAccount();
    blindDateService.createBlindDateFee(account, dto);

    return ResponseDTO.of(BlindDateResponseCode.BLIND_DATE_FEE_CREATED_OK);
  }

  @Operation(summary = "보증금 납부 확인 여부 조회")
  @GetMapping("/fee/confirm")
  public Boolean getIsSubmittedBySeason(@RequestParam Integer season) {
    Account account = accountService.getCurrentAccount();
    return blindDateService.getIsSubmitted(account, season);
  }
}
