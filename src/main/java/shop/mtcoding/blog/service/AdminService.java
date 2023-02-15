package shop.mtcoding.blog.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User 로그인(LoginReqDto loginReqDto) {

        User ADMIN = userRepository.findByUsername(loginReqDto.getUsername());
        if (!ADMIN.getRole().equals("ADMIN")) {
            throw new CustomException("관리자 아이디를 확인해주세요");
        }

        if (!ADMIN.getPassword().equals(loginReqDto.getPassword())) {
            throw new CustomException("관리자 비밀번호가 일치하지 않습니다");
        }
        return ADMIN;
    }

    @Transactional(readOnly = true)
    public void 유저삭제하기(int id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new CustomApiException("삭제할 유저가 존재하지 않습니다");
        }

        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("일시적인 서버문제가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
