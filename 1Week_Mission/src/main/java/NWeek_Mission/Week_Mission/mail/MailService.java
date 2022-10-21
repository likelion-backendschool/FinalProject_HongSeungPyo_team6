package NWeek_Mission.Week_Mission.mail;

import NWeek_Mission.Week_Mission.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") // 보내는 사람 메일 주소
    private String from;


    public void sendMail(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("회원가입을 축하드립니다!");
        simpleMailMessage.setText("회원님의 멋북스 회원가입을 축하드립니다! 항상 더 나은 서비스로 보답하겠습니다. 감사합니다.");

        javaMailSender.send(simpleMailMessage);
    }
    public void sendMail(String email, String subject, String content) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        mimeMessageHelper.setFrom(from); // 보낼 주소
        mimeMessageHelper.setTo(email); // 받을 주소
        mimeMessageHelper.setSubject(subject); // 제목

        StringBuilder body = new StringBuilder();
        body.append(content);
        mimeMessageHelper.setText(body.toString(), true);
        javaMailSender.send(mimeMessage);
    }



}