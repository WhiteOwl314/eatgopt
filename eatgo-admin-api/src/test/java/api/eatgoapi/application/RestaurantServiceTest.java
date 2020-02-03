package api.eatgoapi.application;

import api.eatgoapi.domain.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RestaurantServiceTest {

    private RestaurantService restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        mockRestaurantRepository();

        restaurantService = new RestaurantService(restaurantRepository);
    }


    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
        .id(1004L)
        .name("Bob zip")
        .address("Seoul").build();
        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

    }

    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();


        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId()).isEqualTo(1004L);
    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        assertThat(restaurant.getId()).isEqualTo(1004L);
    }

    @Test
    public void getRestaurantWithNotExisted() {


        Exception exception = assertThrows(
                RestaurantNotFoundException.class, () -> restaurantService.getRestaurant(404L)
        );

        assertTrue(exception.getMessage().contains("Could not find restaurant404"));

    }

    String findById(Long id) throws RestaurantNotFoundException{
        throw new RestaurantNotFoundException(id);
    }

    @Test
    public void addRestaurant(){

        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();

        Restaurant created = restaurantService.addRestaurant(restaurant);



        assertThat(created.getId()).isEqualTo(1234L);
    }

    @Test
    public void updateRestaurant(){

        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob Zip")
                .address("Seoul").build();

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "Sool Zip","Busan");

        assertThat(restaurant.getName()).isEqualTo("Sool Zip");
        assertThat(restaurant.getAddress()).isEqualTo("Busan");
    }
}