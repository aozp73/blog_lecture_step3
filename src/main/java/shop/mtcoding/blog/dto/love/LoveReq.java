package shop.mtcoding.blog.dto.love;

import lombok.Getter;
import lombok.Setter;

public class LoveReq {

    @Getter
    @Setter
    public static class LoveSaveReqDto {
        private Integer boardId;
    }
}
