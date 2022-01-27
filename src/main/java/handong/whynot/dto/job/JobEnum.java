package handong.whynot.dto.job;

import lombok.NoArgsConstructor;

public enum JobEnum {
    developer(1), designer(2), promoter(3), etc(4);

    private int code;

    JobEnum(int code) {
        this.code = code;
    }


}
