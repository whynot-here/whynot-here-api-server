package handong.whynot.service;

import handong.whynot.domain.UserFeedback;
import handong.whynot.dto.admin.UserFeedbackRequestDTO;
import handong.whynot.repository.UserFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserFeedbackService {

    private final UserFeedbackRepository userFeedbackRepository;

    @Transactional
    public void createUserFeedback(UserFeedbackRequestDTO request) {

        UserFeedback feedback = UserFeedback.builder()
                .description(request.getDescription())
                .build();

        userFeedbackRepository.save(feedback);
    }
}
