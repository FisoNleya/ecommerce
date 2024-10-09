package com.manica.productscatalogue.auction.bid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manica.productscatalogue.auction.auction.Auction;
import com.manica.productscatalogue.subscriptions.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bid", uniqueConstraints = {

})
public class Bid implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id", nullable = false)
    private Long bidId;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "auction_id",referencedColumnName = "auction_id", foreignKey = @ForeignKey(name = "bid_auction_id_fk"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user  ;

    @Column(name = "bid_amount")
    private Double bidAmount;


    @CreationTimestamp
    private LocalDateTime createdAt;


}
