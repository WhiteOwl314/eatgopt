package api.eatgoapi.interfaces;

import api.eatgoapi.application.EmailNotExistedException;
import api.eatgoapi.application.PasswordWrongException;
import api.eatgoapi.application.UserService;
import api.eatgoapi.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
public class SessionControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void createWithValidAttributes() throws Exception {
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .password("ACCESSTOKEN")
                .build();
        given(userService.authenticate(email,password)).willReturn(mockUser);

        mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"tester@example.com\",\n" +
                        "  \"password\": \"test\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string("{\"accessToken\":\"ACCESSTOKE\"}"));

        verify(userService).authenticate(eq(email),eq(password));
    }

    @Test
    public void createWithNotExistedEmail() throws Exception {

        given(userService.authenticate("x@example.com", "test"))
                .willThrow(EmailNotExistedException.class);

        mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"x@example.com\",\n" +
                        "  \"password\": \"test\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("x@example.com"),eq("test"));
    }

    @Test
    public void createWrongPassword() throws Exception {

        given(userService.authenticate("tester@example.com", "x"))
                .willThrow(PasswordWrongException.class);

        mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"tester@example.com\",\n" +
                        "  \"password\": \"x\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("tester@example.com"),eq("x"));
    }
}
