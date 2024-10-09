package com.manica.productscatalogue.auction.auction;

import com.manica.productscatalogue.auction.auction.dto.AuctionDto;
import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.auth.dtos.ValidUser;
import com.manica.productscatalogue.exceptions.AccessDeniedException;
import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.exceptions.UnAuthorisedException;
import com.manica.productscatalogue.inventory.product.Product;
import com.manica.productscatalogue.inventory.product.ProductService;
import com.manica.productscatalogue.subscriptions.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ProductService productService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    public Auction createAuction(AuctionDto auctionDto, AuthenticationUser authenticationUser){
        Product product = productService.findById(auctionDto.getProductId());

        verifyProductOwner(auctionDto, authenticationUser, product);

        Auction auction = modelMapper.map(auctionDto, Auction.class);

        auction.setProduct(product);
        auction.setVendorSeller(product.getVendor());
        auction.setSeller(product.getOwner());

       return auctionRepository.save(auction);
    }

    public  Auction findById(Long auctionId){
        return auctionRepository.findById(auctionId).orElseThrow(()->new DataNotFoundException("Auction with ID"+auctionId+" was not found"));
    }

    public Auction updateAuction(AuctionDto auctionDto, Auction auction,AuthenticationUser authenticationUser){
        Product product = auctionDto.getProductId().equals(auction.getProduct().getProductId()) ? auction.getProduct(): productService.findById(auctionDto.getProductId());

        verifyProductOwner(auctionDto, authenticationUser, product);

        auction = modelMapper.map(auctionDto, Auction.class);

        auction.setProduct(product);
        auction.setVendorSeller(product.getVendor());
        auction.setSeller(product.getOwner());

       return auctionRepository.save(auction);
    }

    private void verifyProductOwner(AuctionDto auctionDto, AuthenticationUser authenticationUser, Product product) {
        if (Objects.nonNull(product.getOwner()) && !product.getOwner().getEmail().equalsIgnoreCase(authenticationUser.getEmail()) && Objects.isNull(auctionDto.getVendorId())){
            throw new UnAuthorisedException("User is not allowed to auction this product");
        }

        if (Objects.nonNull(product.getVendor()) && !product.getVendor().getVendorId().equals(auctionDto.getVendorId())){
            throw new UnAuthorisedException("Vendor not authorized to auction this product");
        }
    }

    public Auction updateStatus(AuctionStatus auctionStatus,Auction auction, AuthenticationUser authenticationUser){
        Product product = auction.getProduct();
        var user = userService.findUserByEmail(authenticationUser.getEmail());
        if (!product.getOwner().getEmail().equalsIgnoreCase(authenticationUser.getEmail()) && Objects.isNull(product.getVendor())){
            throw new UnAuthorisedException("User is not allowed to auction this product");
        }

        auction.setAuctionStatus(auctionStatus);
       return auctionRepository.save(auction);
    }


    public Page<Auction> getAuctions(int pageSize, int pageNumber, String search,AuctionStatus auctionStatus,ProductStatus productStatus, String sortBy, Sort.Direction direction){
        Pageable pageable = PageRequest.of(pageNumber,pageSize).withSort(direction,sortBy);

        Specification<Auction> auctionSpecification = AuctionSpecification.hasAuctionStatus(auctionStatus);
        if (Objects.nonNull(productStatus)){
            auctionSpecification = auctionSpecification.and(AuctionSpecification.hasProductStatus(productStatus));
        }
        if (Objects.nonNull(search)){
            auctionSpecification = auctionSpecification.and(AuctionSpecification.hasQuery(search));
        }

        return  auctionRepository.findAll(auctionSpecification,pageable);
    }

}
