package api.eatgoapi.domain;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> findAll();
    Restaurant findMyId(Long id);
}
