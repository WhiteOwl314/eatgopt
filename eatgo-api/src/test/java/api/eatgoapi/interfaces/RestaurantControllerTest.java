package api.eatgoapi.interfaces;

import api.eatgoapi.application.RestaurantService;
import api.eatgoapi.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    //가짜로 투입. SpyBean 은 진짜
    //Repository를 사용하지 않기 때문에 아래 빈들은 다 지움
    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception{
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(
                Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address( "Seoul").build()
        );

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")));
    }

    @Test
    public void detail() throws Exception{
        Restaurant restaurant1 = Restaurant.builder()
                .id(1004L)
                .name("JOKER house")
                .address("Seoul")
                .build();
        restaurant1.setMenuItems(Arrays.asList(MenuItem.builder()
                .name("Kimchi").build()));

        Restaurant restaurant2 = Restaurant.builder()
                .id(2020L)
                .name("Cyber Food")
                .address("Seoul")
                .build();


        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);
        given(restaurantService.getRestaurant(2020L)).willReturn(restaurant2);


        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"JOKER house\"")))
                .andExpect(content().string(containsString("Kimchi")));

        mvc.perform(get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":2020")))
                .andExpect(content().string(containsString("\"name\":\"Cyber Food\"")));
    }

    @Test
    public void createWithValidData() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"BeRyong\",\n" +
                        "  \"address\": \"Busan\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("{}"));

        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();
        verify(restaurantService).addRestaurant(restaurant);
    }

    @Test
    public void createWithInvalidData() throws Exception {

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"\",\n" +
                        "  \"address\": \"\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWhithValidData() throws Exception {
        mvc.perform(patch("/restaurants/1004 ")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"JOKER Bar\",\n" +
                        "  \"address\": \"Busan\"\n" +
                        "}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L, "JOKER Bar", "Busan");
    }

    @Test
    public void updateWhithInvalidData() throws Exception {
        mvc.perform(patch("/restaurants/1004 ")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"\",\n" +
                        "  \"address\": \"\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWhithoutName() throws Exception {
        mvc.perform(patch("/restaurants/1004 ")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"\",\n" +
                        "  \"address\": \"Busan\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }
}