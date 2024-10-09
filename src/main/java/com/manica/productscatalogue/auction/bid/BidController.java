package com.manica.productscatalogue.auction.bid;

import com.manica.productscatalogue.auction.auction.Auction;
import com.manica.productscatalogue.auction.auction.AuctionService;
import com.manica.productscatalogue.auction.bid.dto.AutoBidDto;
import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bid")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class BidController {
    private final BidService bidService;
    private final AuctionService auctionService;
    private final UserService userService;


    @PostMapping("auto-bid")
    public ResponseEntity<AutoBid> setAutoBid(@RequestBody AutoBidDto autoBidDto, AuthenticationUser authenticationUser){
        Auction auction = auctionService.findById(autoBidDto.getAuctionId());
        return new ResponseEntity<>(bidService.createAutoBid(autoBidDto,auction,authenticationUser), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Bid> makeBid(@RequestParam Long auctionId,@RequestParam Double amount,AuthenticationUser authenticationUser){
        Auction auction = auctionService.findById(auctionId);
        User user = userService.findUserByEmail(authenticationUser.getEmail());
        return new ResponseEntity<>(bidService.createBid(auction,amount,user),HttpStatus.OK);
    }

}
