package shop.mtcoding.blog.dto.board;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.blog.util.DataUtil;

public class BoardResp {
    @Getter
    @Setter
    public static class BoardMainRespDto {
        private int id;
        private String title;
        private String thumbnail;
        private String username;
    }

    @Getter
    @Setter
    public static class BoardDetailRespDto {
        private int id;
        private int userId;
        private String title;
        private String content;
        private String username;
    }

    @Getter
    @Setter
    public static class BoardADMINRespDto {
        private int id;
        private String title;
        private String content;
        private String username;
        private Timestamp createdAt;

        public String getCreatedAtToString() {
            return DataUtil.format(createdAt);
        }
    }
}
