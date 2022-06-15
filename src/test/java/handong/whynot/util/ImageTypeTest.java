package handong.whynot.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImageTypeTest {

    @Test
    @DisplayName("MIME 타입 검증 메서드 테스트")
    void isValidFileMimeType() throws IOException {

        INVALID_TYPES().forEach(
                file -> assertEquals(false, ImageType.isValidFileMimeType(file, ImageType.IMAGE_MIME_TYPES)));

        VALID_TYPES().forEach(
                file -> assertEquals(true, ImageType.isValidFileMimeType(file, ImageType.IMAGE_MIME_TYPES)));

    }

    private static List<File> INVALID_TYPES() {
        return List.of(new File("filename.txt"),
                new File("filename.exe"),
                new File("filename.mp4"),
                new File("filename.aaa")
        );
    }

    private static List<File> VALID_TYPES() {
        return List.of(new File("filename.gif"),
                new File("filename.jpeg"),
                new File("filename.png")
        );
    }
}