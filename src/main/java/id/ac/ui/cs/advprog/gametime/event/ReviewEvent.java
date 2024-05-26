package id.ac.ui.cs.advprog.gametime.event;

import id.ac.ui.cs.advprog.gametime.model.Review;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReviewEvent extends ApplicationEvent {
    private final Review review;
    public ReviewEvent(Object source, Review review) {
        super(source);
        this.review = review;
    }
}
