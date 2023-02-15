package shop.mtcoding.blog.dto.admin;

import lombok.Getter;
import lombok.Setter;

public class AdminReq {
    @Setter
    @Getter
    public static class AdminLoginReqDto {
        private String username;
        private String password;
    }
}
