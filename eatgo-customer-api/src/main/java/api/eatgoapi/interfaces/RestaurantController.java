package api.eatgoapi.interfaces;

import api.eatgoapi.application.RestaurantService;
import api.eatgoapi.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    //List
    @GetMapping("/restaurants")
    public List<Restaurant> list(
            @RequestParam("region") String region
    ){
        List<Restaurant> restaurants = restaurantService.getRestaurants(region) ;
        return restaurants;
    }

    //Detail
    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id){
        //기본정보 + 메뉴정보
        Restaurant restaurant = restaurantService.getRestaurant(id);

         return restaurant;
    }
}
