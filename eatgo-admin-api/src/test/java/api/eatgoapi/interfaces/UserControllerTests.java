package api.eatgoapi.interfaces;

import api.eatgoapi.application.UserService;
import api.eatgoapi.domain.User;
import org.apache.catalina.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {

        List<User> users = new ArrayList<>();
        users.add(
                User.builder()
                        .email("tester@example.com")
                        .name("tester")
                        .level(1L)
                        .build()
        );

        given(userService.getUsers()).willReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tester")));
    }

    @Test
    public void create() throws Exception {

        String email = "admin@example.com";
        String name = "Administrator";
        User user = User.builder()
                .email(email)
                .name(name)
                .build();
        given(userService.addUser(email, name)).willReturn(user);
        mvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"admin@example.com\",\n" +
                        "  \"name\": \"Administrator\"\n" +
                        "}"))
                .andExpect(status().isCreated());

        verify(userService).addUser(email, name );
    }

    @Test
    public void update() throws Exception {


        mvc.perform(MockMvcRequestBuilders.patch("/users/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"admin@example.com\",\n" +
                        "  \"name\": \"Administrator\",\n" +
                        "  \"level\": 100\n" +
                        "}"))
                .andExpect(status().isOk());

        Long id = 1004L;
        String email = "admin@example.com";
        String name = "Administrator";
        Long level = 100L;

        verify(userService).updateUser(eq(id), eq(email), eq(name), eq(level));
    }

    @Test
    public void deactivate() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/1004"))
                .andExpect(status().isOk());

         verify(userService).deactiveUser(1004L);
    }
}