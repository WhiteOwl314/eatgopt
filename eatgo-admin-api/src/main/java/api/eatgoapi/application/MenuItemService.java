package api.eatgoapi.application;

import api.eatgoapi.domain.MenuItem;
import api.eatgoapi.domain.MenuItemRepository;
import api.eatgoapi.domain.Restaurant;
import api.eatgoapi.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private MenuItemRepository menuItemRopository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository){
        this.menuItemRopository = menuItemRepository;
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
        for(MenuItem menuItem : menuItems){
            update(restaurantId, menuItem);
        }
    }

    private void update(Long restaurantId, MenuItem menuItem) {
        if(menuItem.isDestroy()){
            menuItemRopository.deleteById(menuItem.getId());
            return;
        }
        menuItem.setRestaurantId(restaurantId);
        menuItemRopository.save(menuItem);
    }

    public List<MenuItem> getMenuitems(Long restaurantId) {
        return menuItemRopository.findAllByRestaurantId(restaurantId);
    }
}
