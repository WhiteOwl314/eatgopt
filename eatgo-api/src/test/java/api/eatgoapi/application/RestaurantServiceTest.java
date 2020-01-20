package api.eatgoapi.application;

import api.eatgoapi.domain.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {

    private RestaurantService restaurantService;
    private RestaurantRepository restaurantRepository;
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setUp(){
        restaurantRepository = new RestaurantRepositoryImpl();
        menuItemRepository = new MenuItemRepositoryImpl();
        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository);
    }

    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId()).isEqualTo(2020L);
    }

    @Test
    public void getRestaurant(){
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        assertThat(restaurant.getId()).isEqualTo(1004L);

        MenuItem menuItem = restaurant.getMenuItems().get(0);

        assertThat(menuItem.getName()).isEqualTo("Kimchi");
    }
}