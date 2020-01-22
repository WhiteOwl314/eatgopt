package api.eatgoapi.interfaces;

import api.eatgoapi.application.RestaurantService;
import api.eatgoapi.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    //List
    @GetMapping("/restaurants")
    public List<Restaurant> list(){
        List<Restaurant> restaurants = restaurantService.getRestaurants() ;
        return restaurants;
    }

    //Detail
    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id){
        //기본정보 + 메뉴정보
        Restaurant restaurant = restaurantService.getRestaurant(id);

         return restaurant;
    }

    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@RequestBody Restaurant resource) throws URISyntaxException {

        String name = resource.getName();
        String address = resource.getAddress();
        Restaurant restaurant = new Restaurant( name, address);
        restaurantService.addRestaurant(restaurant);

        URI location = new URI("/restaurants/" + restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }
}
