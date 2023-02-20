package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.admin.AdminReq.AdminRoleChangeReqDto;
import shop.mtcoding.blog.dto.admin.AdminReq.AdminSendEmailReqDto;
import shop.mtcoding.blog.dto.board.BoardResp.BoardADMINRespDto;
import shop.mtcoding.blog.dto.reply.ReplyResp.ReplyDetailRespDto;
import shop.mtcoding.blog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.ReplyRepository;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.AdminService;
import shop.mtcoding.blog.service.BoardService;
import shop.mtcoding.blog.service.ReplyService;

@RequiredArgsConstructor
@Controller
public class AdminController {

    private final HttpSession session;

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    private final BoardService boardService;
    private final ReplyService replyService;
    private final AdminService adminService;

    @PutMapping("/admin/email")
    public ResponseEntity<?> tes(@RequestBody AdminSendEmailReqDto adminSendEmailReqDto) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("관리자 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        // 유효성 검사
        if (adminSendEmailReqDto.getTitle() == null || adminSendEmailReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("제목을 작성해주세요");
        }
        if (adminSendEmailReqDto.getContent() == null || adminSendEmailReqDto.getContent().isEmpty()) {
            throw new CustomApiException("내용을 작성해주세요");
        }

        adminService.이메일전송(adminSendEmailReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "이메일 전송 성공", null), HttpStatus.OK);
    }

    @GetMapping("admin/search/reply")
    public @ResponseBody String searchUser(Model model, String serachKeyword) {
        Gson gson = new Gson();
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("관리자 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        List<ReplyDetailRespDto> searchDtos = adminService.댓글검색(serachKeyword);

        return gson.toJson(searchDtos);
    }

    @GetMapping("admin/search/board")
    public @ResponseBody String searchBoard(String serachKeyword) {
        Gson gson = new Gson();
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("관리자 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        List<BoardADMINRespDto> searchDtos = adminService.게시물검색(serachKeyword);
        return gson.toJson(searchDtos);
    }

    @PutMapping("admin/user/role")
    public ResponseEntity<?> UpdateUserRole(@RequestBody AdminRoleChangeReqDto adminRoleChangeReqDto) {

        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("관리자 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        adminService.직책변경(adminRoleChangeReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "직책변경 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("admin/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("관리자 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        adminService.유저삭제하기(id);

        return new ResponseEntity<>(new ResponseDto<>(1, "유저삭제 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("admin/board/delete/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable int id) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("관리자 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        boardService.게시글삭제(id, ADMIN.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "게시글삭제 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("admin/reply/delete/{id}")
    public ResponseEntity<?> replyBoard(@PathVariable int id) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("관리자 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        replyService.댓글삭제(id, ADMIN.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "댓글삭제 성공", null), HttpStatus.OK);
    }

    @GetMapping("/admin/loginForm")
    public String adminMain() {

        return "admin/loginForm";
    }

    @GetMapping("/admin/userForm")
    public String adminUserForm(Model model) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomException("관리자 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);

        return "admin/userForm";
    }

    @GetMapping("/admin/boardForm")
    public String adminBoardForm(Model model) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomException("관리자 권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        List<BoardADMINRespDto> boardList = boardRepository.findAllWithUserForADMIN();
        model.addAttribute("boardList", boardList);
        return "admin/boardForm";
    }

    @GetMapping("/admin/replyForm")
    public String adminReplyForm(Model model) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomException("관리자 권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        List<ReplyDetailRespDto> replyList = replyRepository.findAllWithUser();
        model.addAttribute("replyList", replyList);

        return "admin/replyForm";
    }

    // @GetMapping("/admin/main")
    // public String main() {
    // if (session.getAttribute("ADMIN") == null) {
    // throw new CustomException("관리자만 접속가능한 main페이지 입니다");
    // }
    // return "admin/main";
    // }

    @PostMapping("/admin/login") // 유효성 검사, 인증 o, 권한 x
    public String login(LoginReqDto loginReqDto) {

        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("관리자 아이디를 작성해주세요");
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("관리자 비밀번호를 작성해주세요");
        }
        User ADMIN = adminService.로그인(loginReqDto);

        if (!ADMIN.getRole().equals("ADMIN")) {
            throw new CustomException("관리자만 접속가능한 페이지입니다.");
        }

        session.setAttribute("ADMIN", ADMIN);
        return "redirect:/admin/userForm";
    }

    @GetMapping("/admin/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/admin/loginForm";
    }
}
