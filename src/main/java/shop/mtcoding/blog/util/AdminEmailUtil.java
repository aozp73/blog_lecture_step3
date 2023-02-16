package shop.mtcoding.blog.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.mail.SimpleMailMessage;

import shop.mtcoding.blog.dto.admin.AdminReq.AdminSendEmailReqDto;

public class AdminEmailUtil {

    public static SimpleMailMessage sendEmail(AdminSendEmailReqDto adminSendEmailReqDto) {
        List<String> list = new ArrayList<>();
        String beforeParse = adminSendEmailReqDto.getEmailList();

        StringTokenizer st = new StringTokenizer(beforeParse, "/");

        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }

        int userCount = list.size();
        // final String ADDRESS = "aozp73@gmail.com";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo((String[]) list.toArray(new String[userCount]));
        // message.setFrom(ADDRESS);
        message.setSubject(adminSendEmailReqDto.getTitle());
        message.setText(adminSendEmailReqDto.getContent());

        return message;
    }
}
