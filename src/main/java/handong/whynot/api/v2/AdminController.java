package handong.whynot.api.v2;

import handong.whynot.dto.admin.AdminResponseCode;
import handong.whynot.dto.admin.UserFeedbackRequestDTO;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.service.UserFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class AdminController {

    private final UserFeedbackService userFeedbackService;

    @Operation(summary = "사용자 후기 등록")
    @PostMapping("/admin/feedback")
    @ResponseStatus(CREATED)
    public ResponseDTO createUserFeedback(@RequestBody @Valid UserFeedbackRequestDTO request) {

        userFeedbackService.createUserFeedback(request);

        return ResponseDTO.of(AdminResponseCode.ADMIN_USER_FEEDBACK_CREATE_OK);
    }

}
