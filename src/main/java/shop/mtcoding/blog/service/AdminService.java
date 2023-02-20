package shop.mtcoding.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
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
import shop.mtcoding.blog.util.AdminEmailUtil;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminService {

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    private final JavaMailSender javaMailSender;

    @Transactional(readOnly = true)
    public void 이메일전송(AdminSendEmailReqDto adminSendEmailReqDto) {
        // 가져온 이메일이 DB에 존재하는 이메일인지 확인
        String check = "";
        List<String> list = new ArrayList<>();
        list = AdminEmailUtil.parseEmail(adminSendEmailReqDto);

        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            check += user.getEmail() + "/";
        }

        for (int i = 0; i < list.size(); i++) {
            if (!check.contains(list.get(i))) {
                throw new CustomException("회원 소유 이메일이 아닌 곳엔 전송할 수 없습니다. =>" + list.get(i));
            }
        }

        // 이메일 전송
        javaMailSender.send(AdminEmailUtil.sendEmail(adminSendEmailReqDto));
    }

    @Transactional(readOnly = true)
    public List<ReplyDetailRespDto> 댓글검색(String keyword) {
        List<ReplyDetailRespDto> searchDtos = replyRepository.findAllWithUserForSearch(keyword);

        return searchDtos;
    }

    @Transactional(readOnly = true)
    public List<BoardADMINRespDto> 게시물검색(String keyword) {
        List<BoardADMINRespDto> searchDtos = boardRepository.findAllByKeyWordWithUserForADMIN(keyword);

        return searchDtos;
    }

    @Transactional
    public void 직책변경(AdminRoleChangeReqDto adminRoleChangeReqDto) {
        // 변경할 유저 존재 유무
        User targetUser = userRepository.findById(adminRoleChangeReqDto.getChangeUserId());
        if (targetUser == null) {
            throw new CustomApiException("직책 변경할 유저가 존재하지 않습니다");
        }

        // 변경할 직책이 존재하는 직책인지, user & manager 한정되는지 체크 (ADMIN으로 직책변경하는 포스트맨 공격 방지)
        if (!targetUser.getRole().equals("USER") &&
                !targetUser.getRole().equals("manager")) {
            throw new CustomApiException("변경할 수 없는 직책 입니다.");
        }

        // 유저 role 변경
        targetUser.setRole(adminRoleChangeReqDto.getChangeRole());

        try {
            userRepository.updateById(targetUser.getId(),
                    targetUser.getUsername(),
                    targetUser.getPassword(), targetUser.getEmail(), targetUser.getProfile(), targetUser.getRole(),
                    targetUser.getCreatedAt());
        } catch (Exception e) {
            throw new CustomApiException("일시적인 서버문제가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
