package id.ac.ui.cs.advprog.gametime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "seller_response")
@Getter
@Setter
public class SellerResponse {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID responseId;

    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Column
    private String response;

    public SellerResponse(){
        this.responseId = UUID.randomUUID();
    }
}
