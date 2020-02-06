package api.eatgoapi.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findAll();
    Optional<Restaurant> findById(Long id);
    //Optional 은 Restaurant가 직접 있는지 없는지도 판단 .

    Restaurant save(Restaurant restaurant);

    List<Restaurant> findAllByAddressContainingAndCategoryId(
            String region, Long categoryId);
}
