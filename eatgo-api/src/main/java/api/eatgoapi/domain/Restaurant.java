package api.eatgoapi.domain;

import lombok.Data;

@Data
public class Restaurant {
    private  long id;
    private  String name;
    private  String address;

    public Restaurant(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
