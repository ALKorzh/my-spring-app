package com.example.myspringapp.service;

import com.example.myspringapp.model.User;
import com.example.myspringapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserExistsAndConfirmed_ReturnsUserDetails() {
        User user = new User();
        user.setUsername("alex");
        user.setPassword("encodedpass");
        user.setConfirmed(true);

        when(userRepository.findByUsername("alex")).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername("alex");

        assertEquals("alex", result.getUsername());
        assertEquals("encodedpass", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("ghost"));
    }

    @Test
    void loadUserByUsername_UserNotConfirmed_ThrowsException() {
        User user = new User();
        user.setUsername("alex");
        user.setPassword("pass");
        user.setConfirmed(false);

        when(userRepository.findByUsername("alex")).thenReturn(Optional.of(user));

        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("alex"));

        assertEquals("Email not confirmed", ex.getMessage());
    }

    @Test
    void registerUser_ShouldEncodePasswordAndSetTokenAndConfirmedFalse() {
        User inputUser = new User();
        inputUser.setUsername("alex");
        inputUser.setPassword("rawpass");

        when(passwordEncoder.encode("rawpass")).thenReturn("encodedpass");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.registerUser(inputUser);

        assertEquals("encodedpass", savedUser.getPassword());
        assertFalse(savedUser.getConfirmed());
        assertNotNull(savedUser.getConfirmationToken());

        verify(userRepository).save(savedUser);
    }
}
