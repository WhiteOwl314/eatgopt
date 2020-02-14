package api.eatgoapi.interfaces;

import lombok.Data;

@Data
public class SessionRequestDto {

    private String email;

    private String password;
}
