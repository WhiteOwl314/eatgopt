package api.eatgoapi.interfaces;

import api.eatgoapi.application.MenuItemService;
import api.eatgoapi.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @RequestMapping(value="/restaurants/{restaurantId}/menuitems", method = RequestMethod.GET)
    public List<MenuItem> list(@PathVariable Long restaurantId){
        List<MenuItem> menuItems = menuItemService.getMenuitems(restaurantId);
        return menuItems;
    }

    @PatchMapping("/restaurants/{restaurantId}/menuitems")
    public String bulkUpdate(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody List<MenuItem> menuItems
    ){
        menuItemService.bulkUpdate(restaurantId, menuItems);
        return "";
    }
}
