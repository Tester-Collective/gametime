package id.ac.ui.cs.advprog.gametime.restcontroller;

import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewRestControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewRestController reviewRestController;

    private MockMvc mockMvc;
    private UUID responseId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewRestController).build();
        responseId = UUID.randomUUID();
    }

    @Test
    void testDeleteSellerResponse() throws Exception {
        mockMvc.perform(post("/api/review/delete-response/{responseId}", responseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(reviewService, times(1)).deleteSellerResponse(responseId);
    }
}
