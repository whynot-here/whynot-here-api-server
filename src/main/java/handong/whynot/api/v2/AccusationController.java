package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.dto.accusation.AccusationRequestDTO;
import handong.whynot.dto.accusation.AccusationResponseCode;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.service.AccountService;
import handong.whynot.service.AccusationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/accusation")
public class AccusationController {

  private final AccountService accountService;
  private final AccusationService accusationService;

  @Operation(summary = "게시글 신고 생성")
  @PostMapping("")
  public ResponseDTO createAccusation(@RequestBody AccusationRequestDTO request) {
    Account account = accountService.getCurrentAccount();
    accusationService.createAccusation(request, account);

    return ResponseDTO.of(AccusationResponseCode.ACCUSATION_CREATED_OK);
  }
}
