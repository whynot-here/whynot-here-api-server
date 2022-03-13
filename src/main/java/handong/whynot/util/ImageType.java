package handong.whynot.util;

import org.apache.commons.lang.StringUtils;
import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// TODO: 22.02.20. 패키지 위치 재고려
public final class ImageType {

    public static final List<String> IMAGE_MIME_TYPES = new ArrayList<>(
            Arrays.asList("image/gif", "image/jpeg", "image/png", "image/bmp")   // 허용 타입들 여기서 선언
    );

    private ImageType() {}

    // 허용한 파일 타입이 맞는지 검증
    public static Boolean isValidFileMimeType(File file, List<String> MIME_TYPES) throws IOException {
        final String fileMimeType = new Tika().detect(file);

        for (String mimeType : MIME_TYPES) {
            if (StringUtils.equals(mimeType, fileMimeType)) {
                return true;
            }
        }

        return false;
    }
}
