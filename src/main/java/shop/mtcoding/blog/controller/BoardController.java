package shop.mtcoding.blog.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardUpdateReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    // public void hello(HttpServletRequest request, HttpServletResponse response)
    // throws IOException {
    // // title=fsda&content=fdwsa
    // // body로 data가 날라오면 Buffer로 읽어서 1. getReader로 읽음 2. parsing

    // // get요청으로 오면 Buffer로 읽지 않고(Body가 없음) get url로 파싱하여 request.getParameter로 또
    // 파싱

    // // x-www-url 방식
    // // 제일 기본이라 이 파싱방법만 getParameter로 제공 해줌
    // String title = request.getParameter("title");

    // // BufferedReader br = new BufferedReader(new
    // // InputStreamReader(request.getInputStream())); 아래 코드와 동일
    // BufferedReader br = request.getReader();

    // String json = br.readLine(); // body에 json담았으므로 json이 나올 것임
    // ObjectMapper om = new ObjectMapper();
    // BoardUpdateReqDto bu = om.readValue(json, BoardUpdateReqDto.class);
    // // 문자는 의미가 없으므로 ObjectMapper로 파싱해야 함

    // // @RequestBody
    // // 일단 readLine()으로 버퍼로 읽음
    // // content type이 text면 그냥 json에 담아서 줌 (파싱할 필요 x)
    // // content type이 json이면 ObjectMapper로 아래 메서드를 자동으로 진행해 줌
    // // ObjectMapper om = new ObjectMapper();
    // // BoardUpdateReqDto bu = om.readValue(json, BoardUpdateReqDto.class);

    // }

    @PutMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> update(@PathVariable int id,
            @RequestBody BoardUpdateReqDto boardUpdateReqDto) {
        // 인증
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }

        // 유효성 검사
        if (boardUpdateReqDto.getTitle() == null || boardUpdateReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("제목을 입력하세요");
        }

        if (boardUpdateReqDto.getContent() == null || boardUpdateReqDto.getContent().isEmpty()) {
            throw new CustomApiException("내용을 입력하세요");
        }

        // 수정
        boardService.게시글수정(id, boardUpdateReqDto, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "게시글이 수정되었습니다.", null), HttpStatus.OK);
    }

    @DeleteMapping("/board/{id}")
    // ResponseEntity를 걸면 ResponseBody를 붙이지 않아도 data를 리턴할 수 있음
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) {
        // 1. 인증
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        boardService.게시글삭제(id, principal.getId());

        // HttpStatus.OK : 200을 던져 줌
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제성공", null), HttpStatus.OK);
    }

    @PostMapping("/board")
    public String save(BoardSaveReqDto BoardSaveReqDto) {

        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        if (BoardSaveReqDto.getTitle() == null || BoardSaveReqDto.getTitle().isEmpty()) {
            throw new CustomException("title을 작성해주세요");
        }
        if (BoardSaveReqDto.getContent() == null || BoardSaveReqDto.getContent().isEmpty()) {
            throw new CustomException("content를 작성해주세요");
        }
        if (BoardSaveReqDto.getTitle().length() > 100) {
            throw new CustomException("title의 길이가 100자 이하여야 합니다");
        }

        boardService.글쓰기(BoardSaveReqDto, principal.getId());

        return "redirect:/";
    }

    @GetMapping({ "/", "/board" })
    public String main(Model model) {
        // List<BoardMainRespDto> dtos = boardRepository.findAllWithUser();
        // Board board = boardRepository.findById(1);
        // List<BoardMainRespDto> dtos = boardService.게시글목록();

        model.addAttribute("dtos", boardRepository.findAllWithUser());
        return "board/main";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, Model model) {
        model.addAttribute("dto", boardRepository.findByIdWithUser(id));

        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        // 유효성 검사
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomException("없는 게시글을 수정할 수 없습니다");
        }

        // 인증
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }

        // 권한
        if (boardPS.getUserId() != principal.getId()) {
            throw new CustomException("해당 게시물을 수정할 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        model.addAttribute("board", boardPS);
        return "board/updateForm";
    }
}
