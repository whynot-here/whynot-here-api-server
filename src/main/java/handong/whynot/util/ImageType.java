package handong.whynot.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang3.StringUtils;

// TODO: 22.02.20. 패키지 위치 재고려
public final class ImageType {

    public static final List<String> IMAGE_MIME_TYPES = new ArrayList<>(
        Arrays.asList("image/gif", "image/jpeg", "image/png")   // 허용 타입들 여기서 선언
    );

    private ImageType() {}

    // 허용한 파일 타입이 맞는지 검증
    public static Boolean isValidFileMimeType(File file, List<String> MIME_TYPES) {
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        String fileMimeType = fileTypeMap.getContentType(file.getName());
        for (String mimeType : MIME_TYPES) {
            if (StringUtils.equals(mimeType, fileMimeType)) {
                return true;
            }
        }

        return false;
    }
}
