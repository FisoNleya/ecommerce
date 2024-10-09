package com.manica.productscatalogue.auction.bid;

import com.manica.productscatalogue.auction.auction.Auction;
import com.manica.productscatalogue.subscriptions.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutoBidRepository extends JpaRepository<AutoBid, Long> {

    Optional<AutoBid> findFirstByAuctionAndUser(Auction auction, User user);
    Optional<AutoBid> findFirstByAuctionAndMaxAmountIsGreaterThanOrderByMaxAmountDesc(Auction action,Double currentBidAmount);


}