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

    @Setter
    @Getter
    public static class AdminRoleChangeReqDto {
        private int changeUserId;
        private String changeRole;
    }

    @Setter
    @Getter
    public static class AdminSendEmailReqDto {
        private String emailList;

    }
}
