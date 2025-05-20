package com.example.myspringapp.controller;

import com.example.myspringapp.config.TestSecurityConfig;
import com.example.myspringapp.model.User;
import com.example.myspringapp.repository.UserRepository;
import com.example.myspringapp.service.EmailService;
import com.example.myspringapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmailService emailService;

    @Test
    void signupForm_ShouldReturnSignupView() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    void signupSubmit_ValidUser_ShouldRegisterAndSendEmail() throws Exception {
        when(userRepository.findByUsername("alex")).thenReturn(Optional.empty());
        when(userService.registerUser(any(User.class))).thenReturn(new User());

        mockMvc.perform(post("/signup")
                        .param("username", "alex")
                        .param("password", "pass1234")
                        .param("email", "alex@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("signin"))
                .andExpect(model().attributeExists("message"));

        verify(emailService).sendConfirmationEmail(any(User.class));
    }

    @Test
    void signupSubmit_ExistingUser_ShouldShowError() throws Exception {
        when(userRepository.findByUsername("alex")).thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/signup")
                        .param("username", "alex")
                        .param("password", "pass1234")
                        .param("email", "alex@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("usernameError"));
    }

    @Test
    void confirmEmail_ValidToken_ShouldConfirmUser() throws Exception {
        User user = new User();
        user.setConfirmed(false);
        user.setConfirmationToken("token123");

        when(userRepository.findByConfirmationToken("token123")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/confirm").param("token", "token123"))
                .andExpect(status().isOk())
                .andExpect(view().name("signin"))
                .andExpect(model().attributeExists("message"));

        verify(userRepository).save(any(User.class));
    }

    @Test
    void confirmEmail_InvalidToken_ShouldReturnError() throws Exception {
        when(userRepository.findByConfirmationToken("badtoken")).thenReturn(Optional.empty());

        mockMvc.perform(get("/confirm").param("token", "badtoken"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    void signinForm_WithErrorAndLogout_ShouldAddMessages() throws Exception {
        mockMvc.perform(get("/signin")
                        .param("error", "true")
                        .param("logout", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("signin"))
                .andExpect(model().attributeExists("errorMessage", "logoutMessage"));
    }

    @Test
    @WithMockUser(username = "alex")
    void home_WithAuthenticatedUser_ShouldShowHomeView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("username", "alex"));
    }
}

