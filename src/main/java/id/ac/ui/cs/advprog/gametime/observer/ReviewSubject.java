package id.ac.ui.cs.advprog.gametime.observer;

import id.ac.ui.cs.advprog.gametime.model.Review;

public interface ReviewSubject {
    void addObserver(ReviewObserver observer);
    void removeObserver(ReviewObserver observer);
    void notifyObservers(Review review);
}
