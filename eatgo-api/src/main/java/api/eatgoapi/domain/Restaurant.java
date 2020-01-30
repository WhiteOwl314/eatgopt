package api.eatgoapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Restaurant {
    @Id
    @GeneratedValue
    private  Long id;

    @NotEmpty
    private  String name;
    @NotEmpty
    private  String address;

    @Transient
    private List<MenuItem> menuItems ;

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Restaurant(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getInformation() {
        return name + " in " + address;
    }

    public void setMenuItem(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<>(menuItems);
    }


    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }

}
