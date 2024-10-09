package com.manica.productscatalogue.inventory.rating;


import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {


    private final RatingService ratingService;


    @PostMapping("/variant/{variantId}")
    public ResponseEntity<Rating> add(
            @PathVariable @Parameter(example = "21") long variantId,
            Principal principal,
            @RequestBody @Valid RatingRequest request
    ) {

        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(ratingService.add(request, user, variantId), HttpStatus.CREATED);
    }


    @GetMapping("/all/variant/{id}")
    public ResponseEntity<List<Rating>> get(
            @PathVariable @Parameter(example = "21") long id
    ) {
        return new ResponseEntity<>(ratingService.findVariantRatings(id), HttpStatus.OK);
    }



    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteRating(
            @PathVariable @Parameter(example = "21") long id
    ) {
        ratingService.delete(id);
        return ResponseEntity.noContent().build();
    }




}
