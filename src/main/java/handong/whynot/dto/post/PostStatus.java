package handong.whynot.dto.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum PostStatus {

    OPEN("open", true),
    CLOSE("close", false);

    private final String status;
    private final Boolean isRecruiting;

    public static Optional<PostStatus> findBy(String type) {

        if (Arrays.stream(PostStatus.values())
                .anyMatch(it -> it.getStatus().equals(type))) {
            return Optional.of(PostStatus.valueOf(type.toUpperCase()));
        }
        return Optional.empty();
    }
}
