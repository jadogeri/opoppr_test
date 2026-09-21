package com.svlogic.opoppr.api.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class PasswordResetService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final String from;

    public PasswordResetService(
            JavaMailSender mailSender,
            TemplateEngine templateEngine,
            @Value("${opoppr.mail.from:no-reply@opoppr.local}") String from
    ) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.from = from;
    }

    public void sendResetEmail(String email) {
        Context context = new Context();
        context.setVariable("recipient", email);
        context.setVariable("resetUrl", "http://localhost:3000/reset-password?token=demo");
        String html = templateEngine.process("email/password-reset", context);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("OPOPPR password reset");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException exception) {
            throw new IllegalStateException("Unable to compose password reset email", exception);
        }
    }
}