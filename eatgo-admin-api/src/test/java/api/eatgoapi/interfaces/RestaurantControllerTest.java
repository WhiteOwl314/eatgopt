package api.eatgoapi.interfaces;

import api.eatgoapi.application.RestaurantService;
import api.eatgoapi.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception{
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(
                Restaurant.builder()
                        .categoryId(1L)
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
    public void detailWithExisted() throws Exception{
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("JOKER house")
                .address("Seoul")
                .build();

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);


        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"JOKER house\"")));
    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));
        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }


    @Test
    public void createWithValidData() throws Exception {
        //유효한 데이터로 추가하기

        //가정 추가
        given(restaurantService.addRestaurant(any())).will(invocation -> {
           Restaurant restaurant = invocation.getArgument(0);
           return Restaurant.builder()
                   .id(1234L)
                   .categoryId(1L)
                   .name(restaurant.getName())
                   .address(restaurant.getAddress())
                   .build();
        });

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"categoryId\":1,\n" +
                        "  \"name\": \"BeRyong\",\n" +
                        "  \"address\": \"Busan\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());
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
                        "  \"categoryId\": 1, \n" +
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