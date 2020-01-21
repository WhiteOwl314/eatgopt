package api.eatgoapi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Data
@NoArgsConstructor
public class Restaurant {
    private  Long id;
    private  String name;
    private  String address;
    private List<MenuItem> menuItems = new ArrayList<>();

    public Restaurant(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getInformation() {
        return name + " in " + address;
    }

    public void setMenuItem(List<MenuItem> menuItems) {
        for(MenuItem menuItem : menuItems){
            addMenuItem(menuItem);
        }
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
}
