package shop.mtcoding.blog.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.blog.util.DataUtil;

@Setter
@Getter
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String profile; // 사진의 경로 (static/image 폴더에 사진 추가하기)
    private String role;
    private Timestamp createdAt;

    public String getCreatedAtToString() {
        return DataUtil.format(createdAt);
    }
}
