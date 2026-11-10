
package com.vasylyna.taskflowapi.security;

import org.junit.jupiter.api.Test;
import io.jsonwebtoken.ExpiredJwtException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtServiceTest {

    private final JwtService jwtService =
            new JwtService(
                    "this-is-a-test-secret-key-with-at-least-32-bytes",
                    3600000
            );

    @Test
    void shouldExtractEmailFromGeneratedToken() {

        // Arrange
        String email = "test@example.com";

        // Act
        String token = jwtService.generateToken(email);
        String extractedEmail = jwtService.extractEmail(token);

        // Assert
        assertEquals(email, extractedEmail);
    }

    @Test
    void shouldRejectTokenWithInvalidSignature() {

        // Arrange
        String token = jwtService.generateToken("test@example.com");


        JwtService anotherJwtService = new JwtService(
                "another-test-secret-key-with-at-least-48-bytes-for-hs384",
                3600000
        );


        // Act & Assert
        assertThrows(
                io.jsonwebtoken.security.SignatureException.class,
                () -> anotherJwtService.extractEmail(token)
        );
    }

    @Test
    void shouldRejectExpiredToken() throws InterruptedException {

        // Arrange
        JwtService shortLivedJwtService = new JwtService(
                "this-is-a-test-secret-key-with-at-least-32-bytes",
                1
        );

        String token = shortLivedJwtService.generateToken(
                "test@example.com"
        );

        Thread.sleep(1200);

        // Act & Assert
        assertThrows(
                ExpiredJwtException.class,
                () -> shortLivedJwtService.extractEmail(token)
        );
    }

}
