package api.eatgoapi.application;

import api.eatgoapi.domain.User;
import api.eatgoapi.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }


    @Test
    public void authenticateWithValidAttributes(){
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email).build();
        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(),any())).willReturn(true);

        User user = userService.authenticate(email , password);

        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    public void authenticateWithNotExistedEmail(){
        String email = "x@example.com";
        String password = "test";

        given(userRepository.findByEmail(email))
                .willReturn(Optional.empty());

        assertThrows(EmailNotExistedException.class,
                () -> userService.authenticate(email , password));
    }

    @Test
    public void authenticateWithWrongPassword(){
        String email = "tester@example.com";
        String password = "x";

        User mockUser = User.builder()
                .email(email).build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(),any())).willReturn(false);

        assertThrows(PasswordWrongException.class,
                () -> userService.authenticate(email , password));
    }
}