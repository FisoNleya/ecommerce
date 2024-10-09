package com.manica.productscatalogue.auction.bid;

import com.manica.productscatalogue.auction.auction.Auction;
import com.manica.productscatalogue.subscriptions.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AutoBid implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_bid_id", nullable = false)
    private Long autoBidId;

    @Column(name = "max_amount")
    private Double maxAmount;

    @ManyToOne
    @JoinColumn(name = "auction_id",referencedColumnName = "auction_id",foreignKey = @ForeignKey(name = "auction_id_fk"))
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "auto_bid_id_user_id_fk"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
