package api.eatgoapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String email;

    private String name;

    @Column(name = "Restaurant_Id")
    private Long restaurantId;

    @NotNull
    private Long level;

    private String password;

    public boolean isAdmin() {
        return level >= 100;
    }

    public boolean isActive() {
        return level>0;
    }

    public void deactivate() {
        level = 0L;
    }

    public boolean isRestaurantOwner() {
        return level == 50L;
    }

    public void setRestaurantId(Long restaurantId){
        this.level = 50L;
        this.restaurantId = restaurantId;

    }
}
