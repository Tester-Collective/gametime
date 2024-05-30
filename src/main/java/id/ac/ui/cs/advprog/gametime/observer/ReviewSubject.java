package id.ac.ui.cs.advprog.gametime.observer;

import id.ac.ui.cs.advprog.gametime.model.Review;

import java.util.ArrayList;
import java.util.List;

public interface ReviewSubject {
    List<ReviewObserver> observers = new ArrayList<>();
    List<ReviewObserver> getObservers();
    void addObserver(ReviewObserver observer);
    void removeObserver(ReviewObserver observer);
    void notifyObservers(Review review);
}
