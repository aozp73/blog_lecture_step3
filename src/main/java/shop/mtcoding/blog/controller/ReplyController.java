package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.reply.ReplyReq.ReplySaveReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.service.ReplyService;

@Controller
public class ReplyController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ReplyService replyService;

    @DeleteMapping("/reply/{id}")
    public ResponseEntity<?> deleteReply(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        replyService.댓글삭제(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "댓글삭제 성공", null), HttpStatus.OK);
    }

    // 패스 : 개발자마다 Convention이 다름
    // insert는 where절이 없으므로 id를 안 적는 사람도 있고
    // 필요는 없지만 어느 board의 댓글을 달겠다고 명시하는 사람도 있음 "/board/{id}/reply"
    @PostMapping("/reply")
    public String save(ReplySaveReqDto replySaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }

        if (replySaveReqDto.getComment() == null || replySaveReqDto.getComment().isEmpty()) {
            throw new CustomException("comment을 입력하세요");
        }

        if (replySaveReqDto.getBoardId() == null) {
            throw new CustomException("boardId가 넘어오지 않았습니다.");
        }

        replyService.댓글쓰기(replySaveReqDto, principal.getId());

        return "redirect:/board/" + replySaveReqDto.getBoardId();
    }

}
