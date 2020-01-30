package api.eatgoapi.interfaces;

import api.eatgoapi.application.ReviewService;
import api.eatgoapi.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;


    @Test
    public void createWithValidAttributes() throws Exception {

        given(reviewService.addReview(any())).willReturn(
                Review.builder()
                        .id(1004L)
                        .build()
        );

        mockMvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"name\":\"JOKER\",\"score\":3,\"description\":\"Mat-It-Da\"}")
        )
                .andExpect(status().isCreated())
        .andExpect(header().string("location", "/restaurants/1/reviews/1004"));

        verify(reviewService).addReview(any());
    }

    @Test
    public void createWithInvalidAttributes() throws Exception {
        mockMvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {}"))
                .andExpect(status().isBadRequest());

        verify(reviewService, never()).addReview(any());
    }

}