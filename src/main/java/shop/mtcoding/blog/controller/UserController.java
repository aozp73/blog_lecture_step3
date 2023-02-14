package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import shop.mtcoding.blog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @PostMapping("/user/profileUpdate")
    public String profileUpdate(MultipartFile profile) {
        // 인증
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/loginForm";
        }

        if (profile.isEmpty()) {
            throw new CustomException("사진이 전송되지 않았습니다");
        }

        System.out.println("테스트 :" + profile.getContentType());
        // profile에서 사진이 아니면 exception 터트리기 코드 하나 더 필요
        // 이걸 하지 않으면 포스트맨으로 사진 아닌 것을 전송할 수 있음 (여기까지 Controller의 역할)
        if (!profile.getContentType().contains("image")) {
            throw new CustomException("사진 파일만 업로드 할 수 있습니다.");
        }

        User UserPS = userService.프로필사진수정(profile, principal.getId());
        session.setAttribute("principal", UserPS);

        return "redirect:/";
    }

    @GetMapping("/user/profileUpdateForm")
    public String profileUpdateForm(Model model) {
        // 인증
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/loginForm";
        }

        User userPS = userRepository.findById(principal.getId());
        model.addAttribute("user", userPS);
        // 권한
        return "user/profileUpdateForm";
    }

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) {

        if (joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");
        }
        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 작성해주세요");
        }
        if (joinReqDto.getEmail() == null || joinReqDto.getEmail().isEmpty()) {
            throw new CustomException("email을 작성해주세요");
        }

        userService.회원가입(joinReqDto);

        return "redirect:/loginForm"; // 302
    }

    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto) {
        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 작성해주세요");
        }
        User principal = userService.로그인(loginReqDto);
        session.setAttribute("principal", principal);
        return "redirect:/";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
}
