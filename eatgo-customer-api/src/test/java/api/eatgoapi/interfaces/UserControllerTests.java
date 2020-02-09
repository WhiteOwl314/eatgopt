package api.eatgoapi.interfaces;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void create() throws Exception {

        User mockUser = User.builder()
                .id(1004L)
                .email("tester@exemple.com")
                .name("tester")
                .password("test")
                .build();

        given(userService.registerUser("tester@example.com", "tester", "test"))
                .willReturn(mockUser);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"tester@example.com\",\n" +
                        "  \"name\": \"tester\",\n" +
                        "  \"password\": \"test\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/users/1004"));

        verify(userService).registerUser(eq("tester@example.com"), eq("tester"), eq("test"));
    }
}
