package api.eatgoapi.interfaces;

import api.eatgoapi.application.ReviewService;
import api.eatgoapi.domain.Review;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(
            Authentication authentication,
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Review resource
    ) throws URISyntaxException {

        Claims claims = (Claims) authentication.getPrincipal();

        String name = claims.get("name", String.class);
        Integer score = resource.getScore();
        String description = resource.getDescription();

        Review review = reviewService.addReview(
                restaurantId, name, score, description);

        String url = "/restaurants/"+ restaurantId +"/reviews/" + review.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
