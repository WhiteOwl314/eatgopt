package api.eatgoapi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private Long categoryId;

    @NotEmpty
    private  String name;
    @NotEmpty
    private  String address;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuItem> menuItems ;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> reviews;

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

    public void serReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }
}
