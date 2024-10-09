package com.manica.productscatalogue.auction.auction.dto;

import com.manica.productscatalogue.auction.auction.Auction;

import com.manica.productscatalogue.auction.auction.AuctionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * DTO for {@link Auction}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionDto implements Serializable {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate startTime;
    @NotNull(message = "Product cannot be null")
    private Long productId;
    @NotNull(message = "Description cannot be null")
    private String description;

    private String specDoc;
    @NotNull(message = "Quantity cannot be null")
    private Double quantity;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate endTime;
    @NotNull(message = "Starting Bid cannot be missing")
    private Double startingBid;
    @NotNull(message = "Please specify reserve price")
    private Double reservePrice;

    private Double requiredDeposit;

    @NotNull(message = "Auction Status is required")
    private AuctionStatus auctionStatus;

    private Long vendorId;
}