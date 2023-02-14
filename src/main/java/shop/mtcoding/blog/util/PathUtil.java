package shop.mtcoding.blog.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import shop.mtcoding.blog.handler.ex.CustomException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class PathUtil {

    private static String getStaticFolder() {
        // getProperty : \workspace\project_lab\blog_lecture_step2
        return System.getProperty("user.dir") + "\\src\\main\\resources\\static\\";
    }

    // getOriginalFilename() : \images\프로필사진.png
    // getStaticFolder : \workspace\project_lab\blog_lecture_step2

    // imageFilePath
    // =>\workspace\project_lab\blog_lecture_step2\src\main\resources\static\images\프로필사진.png

    public static String writeImageFile(MultipartFile profile) {
        UUID uuid = UUID.randomUUID();

        // 하드 디스크에 저장 될 경로
        String uuidImageRealName = "\\images\\" + uuid + "_" + profile.getOriginalFilename();

        String staticFolder = getStaticFolder();
        Path imageFilePath = Paths.get(staticFolder + "\\" + uuidImageRealName);

        try {
            // getBytes인 이유는 Byte Stream이기 때문임
            // 직접 Buffered Write를 하려면 쓰레드를 만들어야함(아니면 여기서 잠시 멈춤)
            Files.write(imageFilePath, profile.getBytes());
        } catch (Exception e) {
            throw new CustomException("사진을 웹서버에 저장하지 못하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // db에 들어갈 이름은 / 그래야 src에서 찾아냄
        return "/images/" + uuid + "_" + profile.getOriginalFilename();
    }

}