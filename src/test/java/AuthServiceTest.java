import com.berk.userauthservice.dto.AuthResponseLogin;
import com.berk.userauthservice.dto.AuthResponseRegister;
import com.berk.userauthservice.entity.Role;
import com.berk.userauthservice.entity.User;
import com.berk.userauthservice.exception.InvalidCredentialsException;
import com.berk.userauthservice.exception.UsernameAlreadyExistsException;
import com.berk.userauthservice.repository.UserRepository;
import com.berk.userauthservice.security.JwtService;
import com.berk.userauthservice.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//Testing
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldCreateUser_whenUsernameNotExists() {

        // given
        String username = "berk";
        String rawPassword = "123";

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(rawPassword))
                .thenReturn("encoded123");

        // when
        AuthResponseRegister response = authService.register(username, rawPassword);

        // then
        assertEquals(username, response.getUsername());

    }

    @Test
    void register_shouldThrowException_whenUsernameExists() {

        String username = "berk";

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User()));

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            authService.register(username, "123");
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {

        String username = "berk";
        String rawPassword = "123";
        String encodedPassword = "encoded123";

        User user = new User(username, encodedPassword, Role.USER);

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(rawPassword, encodedPassword))
                .thenReturn(true);

        when(jwtService.generateToken(user))
                .thenReturn("fake-jwt-token");

        AuthResponseLogin response = authService.login(username, rawPassword);

        assertEquals("fake-jwt-token", response.getToken());
    }

    @Test
    void login_shouldThrowException_whenPasswordIsInvalid() {

        String username = "berk";
        String rawPassword = "123";
        String encodedPassword = "encoded123";

        User user = new User(username, encodedPassword, Role.USER);

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(rawPassword, encodedPassword))
                .thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> {
            authService.login(username, rawPassword);
        });

        verify(jwtService, never()).generateToken(any(User.class));
    }
}
