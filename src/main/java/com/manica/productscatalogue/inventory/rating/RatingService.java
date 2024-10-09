package com.manica.productscatalogue.inventory.rating;

import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.auth.dtos.ValidUser;
import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.inventory.variant.Variant;
import com.manica.productscatalogue.inventory.variant.VariantService;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper mapper;
    private final UserService userService;
    private final VariantService variantService;

    public Rating add(RatingRequest request, AuthenticationUser user, long variantId) {

        Rating rating = ratingRepository
                .findByCreatedBy_EmailAndVariant_VariantId(user.getEmail(), variantId)
                .orElse(new Rating());


        User savedUser = userService.findUserByEmail(user.getEmail());

        Variant variant = variantService.findById(variantId);

        mapper.update(request, rating);
        rating.setCreatedBy(savedUser);
        rating.setVariant(variant);
        return ratingRepository.save(rating);
    }

    public List<Rating> findVariantRatings(long variantId) {
        return ratingRepository.findByVariant_VariantId(variantId);
    }

    public Rating findRatingById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Rating " + id + " not found"));
    }

    public void delete(long id) {
        Rating rating = findRatingById(id);
        ratingRepository.delete(rating);
    }



}
