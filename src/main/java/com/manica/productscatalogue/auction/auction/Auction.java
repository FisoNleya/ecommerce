package com.manica.productscatalogue.auction.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manica.productscatalogue.auction.bid.Bid;
import com.manica.productscatalogue.inventory.product.Product;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.vendor.Vendor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table()
public class Auction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id", nullable = false)
    private Long auctionId;

    @Column(name = "start_time")
    private LocalDate startTime;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "product_id",referencedColumnName ="product_id" ,foreignKey = @ForeignKey(name = "product_id_auction_id_fk"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @Column(name = "description")
    private String description;
    @Column(name = "spec_doc")
    private String specDoc;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "product_status")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Column(name = "end_time")
    private LocalDate endTime;

    @Column(name = "starting_dib")
    private Double startingBid;

    @Column(name = "reserve_price")
    private Double reservePrice;

    @Column(name = "required_deposit")
    private Double requiredDeposit;


    @ManyToOne
    @JoinColumn(name = "winning_bid_id",referencedColumnName = "bid_id",foreignKey = @ForeignKey(name = "winning_bid_bid_id_fk"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Bid winningBid;


    @ManyToOne
    @JoinColumn(name = "vendor_id",referencedColumnName = "vendor_id",foreignKey = @ForeignKey(name = "vendor_auction_id_fk"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Vendor vendorSeller;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",foreignKey = @ForeignKey(name = "user_id_auction_fk"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User seller;

    @Column(name = "auction_status")
    @Enumerated(EnumType.STRING)
    private AuctionStatus auctionStatus;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
