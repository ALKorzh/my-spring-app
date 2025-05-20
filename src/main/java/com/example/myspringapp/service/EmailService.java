package com.example.myspringapp.service;

import com.example.myspringapp.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.confirmation.url.prefix}")
    private String confirmationUrlPrefix;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(User user) {
        String confirmUrl = confirmationUrlPrefix + user.getConfirmationToken();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Email Confirmation");
        mailMessage.setText("Please confirm your email by clicking the link: " + confirmUrl);
        mailMessage.setFrom(fromEmail);
        mailSender.send(mailMessage);
    }
}

