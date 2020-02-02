package api.eatgoapi.interfaces;

import api.eatgoapi.application.RestaurantService;
import api.eatgoapi.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
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
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource) throws URISyntaxException {

        Restaurant restaurant = Restaurant.builder()
                .name(resource.getName())
                .address(resource.getAddress())
                .build();
        restaurantService.addRestaurant(restaurant);

        URI location = new URI("/restaurants/" + restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/restaurants/{id}")
    public String update( @PathVariable("id") Long id, @Valid @RequestBody Restaurant resaurce){

        String name = resaurce.getName();
        String address = resaurce.getAddress();
        restaurantService.updateRestaurant(id, name, address);

        return "{}";
    }
}
