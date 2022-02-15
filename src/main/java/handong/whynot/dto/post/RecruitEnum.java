package handong.whynot.dto.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum RecruitEnum {

    OPEN("open", true),
    CLOSE("close", false);

    private final String status;
    private final Boolean isRecruiting;

    public static Optional<RecruitEnum> getStatusBy(String type) {

        if (Arrays.stream(RecruitEnum.values())
                .anyMatch(it -> it.getStatus().equals(type))) {
            return Optional.of(RecruitEnum.valueOf(type.toUpperCase()));
        }
        return Optional.empty();
    }
}
