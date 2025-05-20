package com.example.myspringapp.service;

import com.example.myspringapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Устанавливаем значения, которые обычно берутся из application.properties
        emailService = new EmailService(mailSender);
        // Устанавливаем вручную приватные поля через reflection (или можно переделать конструктор)
        setField(emailService, "fromEmail", "no-reply@example.com");
        setField(emailService, "confirmationUrlPrefix", "http://localhost:8080/confirm?token=");
    }

    @Test
    void sendConfirmationEmail_ShouldSendEmailWithCorrectData() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setConfirmationToken("abc123");

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // When
        emailService.sendConfirmationEmail(user);

        // Then
        verify(mailSender, times(1)).send(captor.capture());
        SimpleMailMessage message = captor.getValue();

        String expectedText = "Please confirm your email by clicking the link: http://localhost:8080/confirm?token=abc123";

        assertEquals("test@example.com", message.getTo()[0]);
        assertEquals("Email Confirmation", message.getSubject());
        assertEquals(expectedText, message.getText());
        assertEquals("no-reply@example.com", message.getFrom());
    }

    // Вспомогательный метод для установки значения поля с аннотацией @Value
    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field " + fieldName, e);
        }
    }
}
