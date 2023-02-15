package shop.mtcoding.blog.dto.reply;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.blog.util.DataUtil;

public class ReplyResp {

    @Getter
    @Setter
    public static class ReplyDetailRespDto {
        private Integer id;
        private String comment;
        private Integer userId;
        private Integer boardId;
        private Timestamp createdAt;
        private String username;

        public String getCreatedAtToString() {
            return DataUtil.format(createdAt);
        }
    }
}
