//package id.ac.ui.cs.advprog.gametime.model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ReviewTest {
//
//    private Review review;
//
//    @BeforeEach
//    public void setUp() {
//        review = new Review(UUID.randomUUID(), "Title", 4.5f, "Text");
//    }
//
//    @Test
//    public void testConstructorPositiveRating() {
//        assertEquals(4.5f, review.getRating());
//    }
//
//    @Test
//    public void testConstructorNegativeRating() {
//        assertThrows(IllegalArgumentException.class, () -> new Review(UUID.randomUUID(), "Title", 6.0f, "Text"));
//    }
//
//    @Test
//    public void testAddSellerResponse() {
//        review.addSellerResponse(UUID.fromString("1"), "Response");
//        assertTrue(review.getSellerResponses().containsKey(UUID.fromString("1")));
//        assertEquals("Response", review.getSellerResponses().get(UUID.fromString("1")));
//    }
//
//    @Test
//    public void testRemoveSellerResponse() {
//        review.addSellerResponse(UUID.fromString("1"), "Response");
//        assertTrue(review.getSellerResponses().containsKey(UUID.fromString("1")));
//
//        review.removeSellerResponse(UUID.fromString("1"));
//        assertFalse(review.getSellerResponses().containsKey(UUID.fromString("1")));
//    }
//}
