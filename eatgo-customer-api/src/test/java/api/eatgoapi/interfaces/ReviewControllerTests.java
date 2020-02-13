package api.eatgoapi.interfaces;

import api.eatgoapi.application.ReviewService;
import api.eatgoapi.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;


    @Test
    public void createWithValidAttributes() throws Exception {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0.8hm6ZOJykSINHxL-rf0yV882fApL3hyQ9-WGlJUyo2A";

        given(reviewService.addReview(1L,"John",3,"Good"))
                .willReturn(Review.builder()
                        .id(1004L)
                        .build());

        mockMvc.perform(post("/restaurants/1/reviews")
                .header("Authorization","Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"score\":3,\"description\":\"Good\"}"))
                .andExpect(status().isCreated())
                .andExpect(header()
                        .string(
                                "location",
                                "/restaurants/1/reviews/1004"
                        ));

        verify(reviewService)
                .addReview(
                        1L,
                        "John",
                        3,
                        "Good"
                );
    }

    @Test
    public void createWithInvalidAttributes() throws Exception {
        mockMvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {}"))
                .andExpect(status().isBadRequest());

        verify(reviewService, never()).addReview(any(),any(),any(),any());
    }

}