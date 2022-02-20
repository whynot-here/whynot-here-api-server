package handong.whynot.dto.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum RecruitStatus {

    OPEN("open", true),
    CLOSE("close", false);

    private final String status;
    private final Boolean isRecruiting;

    public static Optional<RecruitStatus> getStatusBy(String type) {

        if (Arrays.stream(values())
                .anyMatch(it -> it.getStatus().equals(type))) {
            return Optional.of(valueOf(type.toUpperCase()));
        }
        return Optional.empty();
    }
}
