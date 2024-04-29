package application.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    public EmailService(JavaMailSender javaMailSender, PasswordEncoder passwordEncoder) {
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendVerificationEmail(String userEmail, String verificationToken) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("code.up@mail.ru");
            helper.setTo(userEmail);
            helper.setSubject("Подтверждение аккаунта CodeUp");

            String htmlContent = loadHtmlContent("templates/email-answer.html");
            htmlContent = htmlContent.replace("${verificationToken}", verificationToken);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendResetPassword(String userEmail) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("code.up@mail.ru");
            helper.setTo(userEmail);
            helper.setSubject("Сброс пароля CodeUp");

            String htmlContent = loadHtmlContent("templates/reset-password.html");
            htmlContent = htmlContent.replace("${userEmail}", passwordEncoder.encode(userEmail));
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String loadHtmlContent(String filename) {
        try {
            ClassPathResource resource = new ClassPathResource(filename);
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
