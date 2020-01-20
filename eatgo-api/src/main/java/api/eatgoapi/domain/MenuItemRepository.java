package api.eatgoapi.domain;

import java.util.List;

public interface MenuItemRepository {

    List<MenuItem> findAllByRestaurantId(Long restaurantId);
}
