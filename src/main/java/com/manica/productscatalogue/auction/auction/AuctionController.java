package com.manica.productscatalogue.auction.auction;

import com.manica.productscatalogue.auction.auction.dto.AuctionDto;
import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auction")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping
    public ResponseEntity<Page<Auction>> getAuction(@RequestParam(defaultValue = "10") Integer pageSize,
                                                    @RequestParam(defaultValue = "0") Integer pageNumber,
                                                    @RequestParam() AuctionStatus auctionStatus,
                                                    @RequestParam(required = false) ProductStatus productStatus,
                                                    @RequestParam(defaultValue = "createdAt") String sortBy,
                                                    @RequestParam(required = false) String query,
                                                    @RequestParam Sort.Direction direction

                                                   ){
        return new ResponseEntity<>(auctionService.getAuctions(pageSize,pageNumber,query,auctionStatus,productStatus,sortBy,direction), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Auction> addAuction(AuthenticationUser principal, @RequestBody AuctionDto auctionDto){
        return new ResponseEntity<>(auctionService.createAuction(auctionDto,principal),HttpStatus.CREATED);
    }

    @PutMapping
    public  ResponseEntity<Auction> updateAuction(AuthenticationUser authentication, @RequestParam Long auctionId, @RequestBody AuctionDto auctionDto){
        Auction auction = auctionService.findById(auctionId);
        return new ResponseEntity<>(auctionService.updateAuction(auctionDto,auction,authentication),HttpStatus.OK);
    }

    @PutMapping("status")
    public ResponseEntity<Auction> updateAuctionStatus(AuthenticationUser authentication, @RequestParam Long auctionId, @RequestParam AuctionStatus auctionStatus){
        return new ResponseEntity<>(auctionService.updateStatus(auctionStatus,auctionService.findById(auctionId),authentication),HttpStatus.OK);
    }

}
