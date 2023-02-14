package shop.mtcoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.reply.ReplyReq.ReplySaveReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.ReplyRepository;

@Transactional(readOnly = true)
@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    // where 절에 걸리는 파라메터를 앞에 받기
    @Transactional
    public void 댓글쓰기(ReplySaveReqDto replySaveReqDto, int principalId) {

        try {
            replyRepository.insert(replySaveReqDto.getComment(), replySaveReqDto.getBoardId(), principalId);
        } catch (Exception e) {
            throw new CustomException("댓글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void 댓글삭제(int id, int principalId) {
        Reply replyPS = replyRepository.findById(id); // 여기서 받는 reply는 Entity임 DB로 받는 것임
        // 삭제할 댓글이 있는지
        if (replyPS == null) {
            throw new CustomApiException("삭제할 댓글이 존재하지 않습니다");
        }

        // 권한 체크
        if (replyPS.getUserId() != principalId) {
            throw new CustomApiException("댓글을 삭제할 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        // 여기까지 넘어오면 1. 인증 OK, 2. 댓글존재유무확인 OK, 3. 권한 OK
        try {
            replyRepository.deleteById(id);
        } catch (Exception e) {
            // log.error("서버에러 : "+e.getMessage());
            // 파일에 버퍼달고, 파일에 쓰기
            // FileWriter fw = new FileWriter("aaa");
            System.out.println("서버 에러" + e.getMessage());
            throw new CustomApiException("일시적인 서버문제가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
