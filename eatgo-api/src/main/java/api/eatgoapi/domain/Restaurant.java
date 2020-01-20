package api.eatgoapi.domain;

import lombok.Data;

@Data
public class Restaurant {
    private  final Long id;
    private  String name;
    private  String address;

    public Restaurant(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getInformation() {
        return name + " in " + address;
    }
}
