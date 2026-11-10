
package com.vasylyna.taskflowapi.service;

import com.vasylyna.taskflowapi.repository.UserRepository;
import com.vasylyna.taskflowapi.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vasylyna.taskflowapi.dto.RegisterRequest;
import com.vasylyna.taskflowapi.entity.User;
import org.mockito.ArgumentCaptor;
import com.vasylyna.taskflowapi.dto.LoginRequest;
import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldCreateAuthService() {
        assertNotNull(authService);
    }

    @Test
    void shouldRegisterNewUser() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("Password123!");

        when(userRepository.existsByEmail("test@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("Password123!"))
                .thenReturn("hashed-password");

        // Act
        authService.register(request);

        // Assert
        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("hashed-password", savedUser.getPassword());

        verify(userRepository).existsByEmail("test@example.com");
        verify(passwordEncoder).encode("Password123!");
    }

    @Test
    void shouldRejectRegistrationWithExistingEmail() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("Password123!");

        when(userRepository.existsByEmail("test@example.com"))
                .thenReturn(true);

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(request)
        );

        verify(userRepository).existsByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void shouldLoginSuccessfully() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("Password123!");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("hashed-password");

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "Password123!",
                "hashed-password"
        )).thenReturn(true);

        when(jwtService.generateToken("test@example.com"))
                .thenReturn("test-jwt-token");

        // Act
        String token = authService.login(request);

        // Assert
        assertEquals("test-jwt-token", token);

        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).matches(
                "Password123!",
                "hashed-password"
        );
        verify(jwtService).generateToken("test@example.com");
    }

    @Test
    void shouldRejectLoginWithWrongPassword() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("WrongPassword123!");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("hashed-password");

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "WrongPassword123!",
                "hashed-password"
        )).thenReturn(false);

        // Act & Assert
        assertThrows(
                BadCredentialsException.class,
                () -> authService.login(request)
        );

        verify(userRepository).findByEmail("test@example.com");

        verify(passwordEncoder).matches(
                "WrongPassword123!",
                "hashed-password"
        );

        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    void shouldRejectLoginWithUnknownEmail() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("unknown@example.com");
        request.setPassword("Password123!");

        when(userRepository.findByEmail("unknown@example.com"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                BadCredentialsException.class,
                () -> authService.login(request)
        );

        verify(userRepository).findByEmail("unknown@example.com");

        verify(passwordEncoder, never())
                .matches(anyString(), anyString());

        verify(jwtService, never())
                .generateToken(anyString());
    }

}
