package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.AccountResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class AccountControllerV2 {

    @Operation(summary = "계정 정보 조회")
    @GetMapping("/account/info")
    public AccountResponseDTO getAccountInfo(HttpServletRequest request) {
        Account account = (Account) request.getAttribute("account");

        return AccountResponseDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .nickname(account.getNickname())
                .profileImg(account.getProfileImg())
                .build();
    }
}
