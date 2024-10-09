package com.manica.productscatalogue.auction.bid;

import com.manica.productscatalogue.auction.auction.Auction;
import com.manica.productscatalogue.auction.auction.AuctionRepository;
import com.manica.productscatalogue.auction.bid.dto.AutoBidDto;
import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.exceptions.UnAuthorisedException;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.user.UserService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final ModelMapper modelMapper;
    private final AutoBidRepository autoBidRepository;
    private final UserService userService;

    @Value("${user.service.auto-bid-increment}")
    private Double autoBidIncrement;

    public  AutoBid createAutoBid(AutoBidDto autoBidDto, Auction auction, AuthenticationUser authenticationUser){
            var autoBid = AutoBid.builder().build();
            var user = userService.findUserByEmail(authenticationUser.getEmail());
            Optional<AutoBid> oldAutoBid = autoBidRepository.findFirstByAuctionAndUser(auction,user);

            oldAutoBid.ifPresent(bid -> autoBid.setAutoBidId(bid.getAutoBidId()));

            autoBid.setAuction(auction);
            autoBid.setUser(user);
            autoBid.setMaxAmount(autoBidDto.getMaxAmount());

            return autoBidRepository.save(autoBid);

    }



    public Bid createBid(Auction auction, Double amount, User user){

        if (auction.getStartTime().isBefore(LocalDate.now()) )
            throw new UnAuthorisedException("Bidding has not yet started for this product");

        if (auction.getEndTime().isAfter(LocalDate.now()))
            throw new UnAuthorisedException("Bidding has already closed for this product");

        Bid bid = Bid.builder()
                .bidAmount(amount)
                .auction(auction)
                .user(user)
                .build();


        if (Objects.isNull(auction.getWinningBid())){
             auction.setWinningBid(bid);
             return bidRepository.save(bid);
        }

        if (amount<auction.getWinningBid().getBidAmount()){
           return bidRepository.save(bid);
        }


        Optional<AutoBid> autoBidOptional = autoBidRepository.findFirstByAuctionAndMaxAmountIsGreaterThanOrderByMaxAmountDesc(auction,amount);
        AutoBid autoBid = null;

        if(autoBidOptional.isEmpty()){
            bid = bidRepository.save(bid);
            auction.setWinningBid(bid);
            return bid;
        }else {
            autoBid = autoBidOptional.get();
        }
        double autoBidAmount = amount + autoBidIncrement;

        bidRepository.save(bid);

        return  createBid(auction, (autoBidAmount <= autoBid.getMaxAmount()) ? autoBidAmount : autoBid.getMaxAmount(),autoBid.getUser());
    }



}
