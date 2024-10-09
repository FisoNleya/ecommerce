package com.manica.productscatalogue.auction.bid.dto;

import com.manica.productscatalogue.auction.bid.AutoBid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link AutoBid}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoBidDto implements Serializable {
    @NotNull
    private Double maxAmount;
    @NotNull
    private Long auctionId;
}