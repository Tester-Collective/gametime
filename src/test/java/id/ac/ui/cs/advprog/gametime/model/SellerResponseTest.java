package id.ac.ui.cs.advprog.gametime.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SellerResponseTest {

    private SellerResponse sellerResponse;
    private UUID responseId;

    @Mock
    private Review review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        responseId = UUID.randomUUID();
        sellerResponse = new SellerResponse();
        sellerResponse.setResponseId(responseId);
        sellerResponse.setReview(review);
        sellerResponse.setResponse("Thank you for your review.");
    }

    @Test
    void testDefaultConstructor() {
        SellerResponse defaultResponse = new SellerResponse();
        assertNotNull(defaultResponse.getResponseId());
        assertNull(defaultResponse.getReview());
        assertNull(defaultResponse.getResponse());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(responseId, sellerResponse.getResponseId());
        assertEquals(review, sellerResponse.getReview());
        assertEquals("Thank you for your review.", sellerResponse.getResponse());

        UUID newId = UUID.randomUUID();
        Review newReview = new Review();
        String newResponse = "This is a new response.";

        sellerResponse.setResponseId(newId);
        sellerResponse.setReview(newReview);
        sellerResponse.setResponse(newResponse);

        assertEquals(newId, sellerResponse.getResponseId());
        assertEquals(newReview, sellerResponse.getReview());
        assertEquals(newResponse, sellerResponse.getResponse());
    }
}
