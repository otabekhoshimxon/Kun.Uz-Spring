package uz.kun.service;
//User :Lenovo
//Date :24.06.2022
//Time :16:48
//Project Name :Kun.uz

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uz.kun.entity.EmailHistoryEntity;
import uz.kun.repository.EmailHistoryRepository;
import uz.kun.util.HttpHeaderUtil;
import uz.kun.util.JWTUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.*;
import java.util.List;

@Service
@Slf4j
public class MailServise {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromAccount;
    @Value("${url2}")
    private String serverUrl;


    public void sendRegistrationEmail(String toAccount, Integer id) {
        String encode = JWTUtil.encode(id);
        String url = String.format("<a href='%s/auth/email/verification/%s'>Link</a>", serverUrl, encode);

        StringBuilder builder = new StringBuilder();
        builder.append("<br>");
        builder.append("<h1 style='align-text:center' color='red' > Assalomu alaykum</h1>");
        builder.append("<b> Ro'yxatdan o'tish uchun quyidagi linkga o'ting </b>");
        builder.append("<br>");
        builder.append("<p>");
        builder.append(url);
        builder.append("</p>");
        builder.append("<br>");

        log.info("Send message email to {}" ,toAccount,builder.toString());
        sendEmail(toAccount, " Ro'yxatdan o'tish ", builder.toString());
    }

    public void sendEmail(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
            EmailHistoryEntity emailHistory=new EmailHistoryEntity();
            emailHistory.setEmail(toAccount);
            emailHistory.setMessage(text);
            emailHistoryRepository.save(emailHistory);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendSimpleEmail(String toAccount, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(text);
        msg.setFrom(fromAccount);
        javaMailSender.send(msg);
    }

    public Long getEmailCount(String email) {
        return emailHistoryRepository.countByEmail(email);
    }

    public PageImpl getPage(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
       Page<EmailHistoryEntity> emailHistoryEntities =emailHistoryRepository.getAll(pageable);
        return new PageImpl((List) emailHistoryEntities,pageable,emailHistoryEntities.getTotalPages());
    }
}
