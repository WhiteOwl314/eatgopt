package api.eatgoapi.interfaces;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionResponseDto {

    private String accessToken;
}
