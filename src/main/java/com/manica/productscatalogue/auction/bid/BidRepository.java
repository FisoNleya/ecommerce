package com.manica.productscatalogue.auction.bid;

import com.manica.productscatalogue.auction.auction.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    Optional<Bid> findFirstByAuctionOrderByBidAmountDesc(Auction auction);
}