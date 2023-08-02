package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.dto.blind_date.BlindDateRequestDTO;
import handong.whynot.dto.blind_date.BlindDateResponseCode;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.service.AccountService;
import handong.whynot.service.BlindDateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
  public ResponseDTO createBlindDate(@RequestBody BlindDateRequestDTO request) {

    Account account = accountService.getCurrentAccount();
    blindDateService.createBlindDate(request, account);

    return ResponseDTO.of(BlindDateResponseCode.BLIND_DATE_CREATED_OK);
  }
}
