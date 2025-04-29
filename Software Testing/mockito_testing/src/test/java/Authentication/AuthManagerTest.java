package Authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthManagerTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private Hash hash;

    @InjectMocks
    private AuthManager authManager;

    private User realUser;
    private final String actualEmail = "test@example.com";
    private final String fakeEmail = "testMail";
    private final String actualPassword = "password123";
    private final String wrongPassword = "wrongpassword";
    private final String actualHashed = "hashed123";

    @BeforeEach
    void setUp() {
        realUser = new User(actualEmail, actualHashed);

        when(userRepo.findByEmail(actualEmail)).thenReturn(realUser);
        when(hash.verify(actualPassword, actualHashed)).thenReturn(true);
        when(hash.verify(wrongPassword, actualHashed)).thenReturn(false);
        when(userRepo.findByEmail(fakeEmail)).thenThrow(new RuntimeException("User not found"));
    }

    @Test
    void testLoginSuccess() {
        assertTrue(authManager.login(actualEmail, actualPassword));
    }

    @Test
    void testLoginFail_UserNotFound() {
        assertThrows(RuntimeException.class, () -> {
            authManager.login(fakeEmail, actualPassword);
        });
    }

    @Test
    void testLoginFail_WrongPassword() {
        assertFalse(authManager.login(actualEmail, wrongPassword));
    }
}