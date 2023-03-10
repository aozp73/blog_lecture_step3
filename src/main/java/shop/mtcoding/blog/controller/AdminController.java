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
            throw new CustomApiException("????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }

        // ????????? ??????
        if (adminSendEmailReqDto.getTitle() == null || adminSendEmailReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("????????? ??????????????????");
        }
        if (adminSendEmailReqDto.getContent() == null || adminSendEmailReqDto.getContent().isEmpty()) {
            throw new CustomApiException("????????? ??????????????????");
        }

        adminService.???????????????(adminSendEmailReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "????????? ?????? ??????", null), HttpStatus.OK);
    }

    @GetMapping("admin/search/reply")
    public @ResponseBody String searchUser(Model model, String serachKeyword) {
        Gson gson = new Gson();
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }

        List<ReplyDetailRespDto> searchDtos = adminService.????????????(serachKeyword);

        return gson.toJson(searchDtos);
    }

    @GetMapping("admin/search/board")
    public @ResponseBody String searchBoard(String serachKeyword) {
        Gson gson = new Gson();
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }

        List<BoardADMINRespDto> searchDtos = adminService.???????????????(serachKeyword);
        return gson.toJson(searchDtos);
    }

    @PutMapping("admin/user/role")
    public ResponseEntity<?> UpdateUserRole(@RequestBody AdminRoleChangeReqDto adminRoleChangeReqDto) {

        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }

        adminService.????????????(adminRoleChangeReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "???????????? ??????", null), HttpStatus.OK);
    }

    @DeleteMapping("admin/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }

        adminService.??????????????????(id);

        return new ResponseEntity<>(new ResponseDto<>(1, "???????????? ??????", null), HttpStatus.OK);
    }

    @DeleteMapping("admin/board/delete/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable int id) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }

        boardService.???????????????(id, ADMIN.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "??????????????? ??????", null), HttpStatus.OK);
    }

    @DeleteMapping("admin/reply/delete/{id}")
    public ResponseEntity<?> replyBoard(@PathVariable int id) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomApiException("????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }

        replyService.????????????(id, ADMIN.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "???????????? ??????", null), HttpStatus.OK);
    }

    @GetMapping("/admin/loginForm")
    public String adminMain() {

        return "admin/loginForm";
    }

    @GetMapping("/admin/userForm")
    public String adminUserForm(Model model) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomException("????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }

        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);

        return "admin/userForm";
    }

    @GetMapping("/admin/boardForm")
    public String adminBoardForm(Model model) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomException("????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }
        List<BoardADMINRespDto> boardList = boardRepository.findAllWithUserForADMIN();
        model.addAttribute("boardList", boardList);
        return "admin/boardForm";
    }

    @GetMapping("/admin/replyForm")
    public String adminReplyForm(Model model) {
        User ADMIN = (User) session.getAttribute("ADMIN");
        if (ADMIN == null) {
            throw new CustomException("????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }
        List<ReplyDetailRespDto> replyList = replyRepository.findAllWithUser();
        model.addAttribute("replyList", replyList);

        return "admin/replyForm";
    }

    // @GetMapping("/admin/main")
    // public String main() {
    // if (session.getAttribute("ADMIN") == null) {
    // throw new CustomException("???????????? ??????????????? main????????? ?????????");
    // }
    // return "admin/main";
    // }

    @PostMapping("/admin/login") // ????????? ??????, ?????? o, ?????? x
    public String login(LoginReqDto loginReqDto) {

        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("????????? ???????????? ??????????????????");
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("????????? ??????????????? ??????????????????");
        }
        User ADMIN = adminService.?????????(loginReqDto);

        if (!ADMIN.getRole().equals("ADMIN")) {
            throw new CustomException("???????????? ??????????????? ??????????????????.");
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
